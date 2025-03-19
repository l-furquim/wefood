package com.lucas.profile_ms.services.impl;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.*;
import com.lucas.profile_ms.domains.profile.enums.ProfileType;
import com.lucas.profile_ms.domains.profile.exceptions.ConfirmationCodeDoesntExists;
import com.lucas.profile_ms.domains.profile.exceptions.InvalidDataCreateProfileException;
import com.lucas.profile_ms.domains.profile.exceptions.ProfileAlredyExistsException;
import com.lucas.profile_ms.domains.profile.exceptions.ProfileNotFoundException;
import com.lucas.profile_ms.repositories.ProfileRepository;
import com.lucas.profile_ms.services.IProfileService;
import com.lucas.profile_ms.services.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class ProfileServiceImpl implements IProfileService {

    private final ProfileRepository profileRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;
    private final KafkaTemplate<String, SendMailConfirmationDto> mailTemplate;
    private static final String MAIL_TOPIC = "mail.request";



    public ProfileServiceImpl(
            ProfileRepository profileRepository,
            RedisTemplate<String, String> redisTemplate,
            AuthenticationManager authenticationManager,
            ITokenService tokenService,
            KafkaTemplate<String, SendMailConfirmationDto> mailTemplate
    ) {
        this.profileRepository = profileRepository;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.mailTemplate = mailTemplate;
    }

    @Override
    public Profile createConfirmation(CreateProfileDto data) {
        if(data.email() == null || data.email().isEmpty() || !data.email().contains("@") ||
            data.username() == null || data.username().isEmpty() ||
            data.password() == null || data.password().isEmpty()){

            throw new InvalidDataCreateProfileException("Invalid data during profile creation");
        }

        var profileAlredyExists = profileRepository.findByEmail(data.email()).isPresent();

        if(profileAlredyExists){
            throw new ProfileAlredyExistsException("A profile already exists with this email");
        }

        var profile = Profile.builder()
                .id(UUID.randomUUID().toString())
                .email(data.email())
                .password(new BCryptPasswordEncoder().encode(data.password()))
                .username(data.username())
                .createdAt(LocalDateTime.now())
                .type(ProfileType.WAITING_CONFIRMATION)
                .build();

        var confirmationCode = String.valueOf(ThreadLocalRandom.current().nextInt(
                11111,
                99999
        ));

        mailTemplate.send(
                MAIL_TOPIC,
                new SendMailConfirmationDto(
                        profile.getEmail(),
                        "furquimmsw@gmail.com",
                        "Confirmação de email - Wefood",
                        "Ola ! Estamos felizes que voce quer fazer parte do wefood, mas antes preciso que confirme que este email é seu. \n" + "Aqui seu código: " + confirmationCode,
                        profile.getId(),
                        "PROFILE"
                )
        );

        log.info("Producer: Solicitação de envio do email de confirmação enviada");

        redisTemplate.opsForValue().set(data.email() + confirmationCode, "valid", 1000);
        profileRepository.save(profile);

        return profile;
    }

    @Transactional
    @Override
    public void confirmCode(ConfirmCodeDto data) {
        var key = data.email() + data.code();

        if(!redisTemplate.hasKey(key)){
            throw new ConfirmationCodeDoesntExists("That code does not exists");
        }

        var profile = profileRepository.findByEmail(data.email());

        profile.get().setType(ProfileType.COMUM);

        profileRepository.save(profile.get());

        redisTemplate.delete(key);
    }

    @Override
    public void delete(DeleteProfileDto data) {

    }

    @Override
    public Profile findById(String id) {
        var profile = profileRepository.findById(id);

        return profile.orElse(null);
    }

    @Override
    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    @Override
    public UserDetails findByEmailDetails(String email) {
        return profileRepository.findByEmailDetails(email);
    }

    @Override
    public String auth(AuthProfileDto data) {
        if(data.email() == null || data.email().isEmpty() || !data.email().contains("@") ||
                data.password() == null || data.password().isEmpty()){

            throw new InvalidDataCreateProfileException("Invalid data during profile authentication");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                data.email(),
                data.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);

        var user = (Profile) auth.getPrincipal();

        if(!redisTemplate.hasKey(user.getId())){
            var token = tokenService.generateToken(user);

            redisTemplate.opsForValue().set(
                    user.getId(),
                    token,
                    Duration.ofHours(2)
            );

            return token;
        }
        return redisTemplate.opsForValue().get(user.getId());
    }

    @Override
    public String getEmail(String userId) {
        var profile = profileRepository.findById(userId);

        if(profile.isEmpty()){
            throw new ProfileNotFoundException("Cannot found the profile");
        }

        return profile.get().getEmail();
    }
}
