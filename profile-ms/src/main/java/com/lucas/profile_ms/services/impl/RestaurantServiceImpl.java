package com.lucas.profile_ms.services.impl;

import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.profile.dto.SendMailConfirmationDto;
import com.lucas.profile_ms.domains.profile.exceptions.ConfirmationCodeDoesntExists;
import com.lucas.profile_ms.domains.restaurant.Restaurant;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantDto;
import com.lucas.profile_ms.domains.restaurant.dto.DeleteRestaurantDto;
import com.lucas.profile_ms.domains.restaurant.enums.RestaurantAccountType;
import com.lucas.profile_ms.domains.restaurant.exceptions.*;
import com.lucas.profile_ms.repositories.RestaurantRepository;
import com.lucas.profile_ms.services.IRestaurantService;
import com.lucas.profile_ms.services.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class RestaurantServiceImpl implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;
    private final KafkaTemplate<String, SendMailConfirmationDto> mailTemplate;
    private static final String MAIL_TOPIC = "mail.request";

    public RestaurantServiceImpl
            (
                    RedisTemplate<String, String> redisTemplate,
                    AuthenticationManager authenticationManager,
                    ITokenService tokenService,
                    KafkaTemplate<String, SendMailConfirmationDto> mailTemplate,
                    RestaurantRepository restaurantRepository
            ) {

        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.mailTemplate = mailTemplate;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant createConfirmation(CreateRestaurantDto data) {
        if(     data.domainEmail() == null || data.domainEmail().isEmpty() ||
                data.name() == null || data.name().isEmpty() ||
                data.password() == null || data.password().isEmpty() ||
                data.cnpj() == null || data.cnpj().isEmpty() ||
                data.address() == null || data.address().isEmpty()
        ){

            throw new InvalidDataCreateRestaurantException("Invalid data during profile creation");
        }

        var restaurants = restaurantRepository.findByEmail(data.domainEmail());

        if(restaurants.isPresent()){
            var mainRestaurant = restaurants.get().stream().filter(
                    Restaurant::getIsMainAccount
            ).toList();

            if(mainRestaurant.get(0) != null && data.isMainAccount()){
                throw new RestaurantAlreadyExistsException("A main restaurant account already exists with this cnpj.");
            }

            if(mainRestaurant.get(0) != null && mainRestaurant.get(0).getAccountType().equals(RestaurantAccountType.WAITING_CONFIRMATION)){
                throw new InvalidDataCreateRestaurantException("You cant create a branch account without making the confirmation of the main account.");
            }
        }

        var restaurant = Restaurant.builder()
                .domainEmail(data.domainEmail())
                .name(data.name())
                .accountType(RestaurantAccountType.WAITING_CONFIRMATION)
                .address(data.address())
                .cnpj(data.cnpj())
                .createdAt(LocalDateTime.now())
                .password(new BCryptPasswordEncoder().encode(data.password()))
                .isMainAccount(data.isMainAccount())
                .build();

        var confirmationCode = String.valueOf(ThreadLocalRandom.current().nextInt(
                11111,
                99999
        ));

        mailTemplate.send(
                MAIL_TOPIC,
                new SendMailConfirmationDto(
                        restaurant.getDomainEmail(),
                        "furquimmsw@gmail.com",
                        "Confirmação de email - Wefood",
                        "Ola ! Estamos felizes que voce quer fazer parte do wefood, mas antes preciso que confirme que este email é seu. \n" + "Aqui seu código: " + confirmationCode,
                        restaurant.getId().toString(),
                        "PROFILE"
                )
        );

        log.info("Producer: Solicitação de envio do email de confirmação enviada");

        redisTemplate.opsForValue().set(data.domainEmail() + confirmationCode, "valid", 1000);
        restaurantRepository.save(restaurant);

        return restaurant;
    }
    @Transactional
    @Override
    public void confirmCode(ConfirmCodeDto data) {
        var key = data.email() + data.code();

        if(!redisTemplate.hasKey(key)){
            throw new ConfirmationCodeDoesntExists("That code does not exists");
        }

        var restaurant = restaurantRepository.findByEmail(data.email()).get().get(0);

        restaurant.setAccountType(restaurant.getIsMainAccount() ? RestaurantAccountType.MAIN : RestaurantAccountType.BRANCH);

        restaurantRepository.save(restaurant);

        redisTemplate.delete(key);
    }
    @Transactional
    @Override
    public void delete(DeleteRestaurantDto data) {
        if(
                data.cnpj() == null || data.cnpj().isEmpty() ||
                        data.password() == null || data.password().isEmpty()
        ){
            throw new InvalidDataDeleteRestaurantException("Invalid data during the deletion of the restaurant");
        }

        var restaurant = restaurantRepository.findByCnpj(data.cnpj());

        if(restaurant.isEmpty()){
            throw new RestaurantAlreadyExistsException("Could not find the restaurant for the deletion");
        }

        if(!restaurant.get().getPassword().equals(data.password())){
            throw new PasswordMismatchException("Wrong password for deletion");
        }

        restaurantRepository.delete(restaurant.get());
    }

    @Override
    public Restaurant findById(Long id) {
        var restaurant = restaurantRepository.findById(id);

        return restaurant.orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getAllBranchAccounts(String domainEmail) {
        var restaurant = restaurantRepository.findByEmail(domainEmail);

        if(restaurant.isEmpty()){
            throw new RestaurantNotFoundException("Could not found the restaurant.");
        }

        return restaurant.get().stream().filter(
                r -> r.getIsMainAccount().equals(false)
        ).toList();
    }
}
