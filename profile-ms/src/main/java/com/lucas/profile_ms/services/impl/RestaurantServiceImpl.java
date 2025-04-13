package com.lucas.profile_ms.services.impl;

import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.profile.dto.SendMailConfirmationDto;
import com.lucas.profile_ms.domains.profile.exceptions.ConfirmationCodeDoesntExists;
import com.lucas.profile_ms.domains.restaurant.Restaurant;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantDto;
import com.lucas.profile_ms.domains.restaurant.dto.DeleteRestaurantDto;
import com.lucas.profile_ms.domains.restaurant.dto.GetRestaurantImagesDto;
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
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private static final String BUCKET = "furqas-wefood-buckets";
    private final S3Client s3Client;

    public RestaurantServiceImpl
            (
                    RedisTemplate<String, String> redisTemplate,
                    AuthenticationManager authenticationManager,
                    ITokenService tokenService,
                    KafkaTemplate<String, SendMailConfirmationDto> mailTemplate,
                    RestaurantRepository restaurantRepository,
                    S3Client s3Client
            ) {

        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.mailTemplate = mailTemplate;
        this.restaurantRepository = restaurantRepository;
        this.s3Client = s3Client;
    }

    @Override
    public Restaurant createConfirmation(CreateRestaurantDto dto) {
        if(     dto.data().domainEmail() == null || dto.data().domainEmail().isEmpty() ||
                dto.data().name() == null || dto.data().name().isEmpty() ||
                dto.data().password() == null || dto.data().password().isEmpty() ||
                dto.data().cnpj() == null || dto.data().cnpj().isEmpty() ||
                dto.data().address() == null || dto.data().address().isEmpty()
        ){

            throw new InvalidDataCreateRestaurantException("Invalid data during profile creation");
        }

        log.info("Email atual: {}", dto.data().domainEmail());

        var restaurants = restaurantRepository.findByEmail(dto.data().domainEmail());

        if(!restaurants.get().isEmpty()){
            var mainRestaurant = restaurants.get().stream().filter(
                    Restaurant::getIsMainAccount
            ).toList();

            if(mainRestaurant.get(0) != null && dto.data().isMainAccount()){
                throw new RestaurantAlreadyExistsException("A main restaurant account already exists with this cnpj.");
            }

            if(mainRestaurant.get(0) != null && mainRestaurant.get(0).getAccountType().equals(RestaurantAccountType.WAITING_CONFIRMATION)){
                throw new InvalidDataCreateRestaurantException("You cant create a branch account without making the confirmation of the main account.");
            }
        }

        var restaurant = Restaurant.builder()
                .domainEmail(dto.data().domainEmail())
                .name(dto.data().name())
                .accountType(RestaurantAccountType.WAITING_CONFIRMATION)
                .address(dto.data().address())
                .cnpj(dto.data().cnpj())
                .createdAt(LocalDateTime.now())
                .password(new BCryptPasswordEncoder().encode(dto.data().password()))
                .isMainAccount(dto.data().isMainAccount())
                .build();

        var restaurantEntity = restaurantRepository.save(restaurant);

        saveRestaurantImage(dto.image(), restaurantEntity.getId());

        var confirmationCode = String.valueOf(ThreadLocalRandom.current().nextInt(
                11111,
                99999
        ));

//        mailTemplate.send(
//                MAIL_TOPIC,
//                new SendMailConfirmationDto(
//                        restaurant.getDomainEmail(),
//                        "furquimmsw@gmail.com",
//                        "Confirmação de email - Wefood",
//                        "Ola ! Estamos felizes que voce quer fazer parte do wefood, mas antes preciso que confirme que este email é seu. \n" + "Aqui seu código: " + confirmationCode,
//                        restaurant.getId().toString(),
//                        "PROFILE"
//                )
//        );

        log.info("Producer: Solicitação de envio do email de confirmação enviada");

        redisTemplate.opsForValue().set(dto.data().domainEmail() + confirmationCode, "valid", 1000);
        return restaurantEntity;
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

    @Override
    public GetRestaurantImagesDto getRestaurantImages(Long id) {
        var restaurant = restaurantRepository.findById(id);

        if(restaurant.isEmpty()) return null;

        List<String> imagesUrl = new ArrayList<>();

        ListObjectsV2Request objectsListRequest = ListObjectsV2Request.builder()
                .bucket(BUCKET)
                .prefix("public/"+ id)
                .build();

        ListObjectsV2Response objectsListResponse = s3Client.listObjectsV2(objectsListRequest);

        List<S3Object> imagesList = objectsListResponse.contents();

        for (S3Object s3Object : imagesList) {
            imagesUrl.add("https://" + BUCKET + ".s3.us-east-1.amazonaws.com/" + s3Object.key());
        }

        return new GetRestaurantImagesDto(imagesUrl);
    }

    private void saveRestaurantImage(MultipartFile image, Long resturantId){
        new Thread(() -> {
            try{
                var imageBytes = image.getBytes();

                log.info("Content type da imagem atual: {}", image.getContentType());

                PutObjectRequest put = PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .contentType(image.getContentType())
                        .key("public/" + resturantId + "/" + image.getOriginalFilename())
                        .build();

                s3Client.putObject(
                        put,
                        RequestBody.fromBytes(
                                imageBytes
                        )
                );

            }catch (IOException e){
                log.error("Erro ao salvar imagem do restaurante" + e.getMessage());
            }
        }).start();
    }
}
