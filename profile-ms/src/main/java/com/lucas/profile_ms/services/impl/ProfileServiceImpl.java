package com.lucas.profile_ms.services.impl;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.AuthProfileDto;
import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.profile.dto.CreateProfileDto;
import com.lucas.profile_ms.domains.profile.dto.DeleteProfileDto;
import com.lucas.profile_ms.domains.profile.enums.ProfileType;
import com.lucas.profile_ms.domains.profile.exceptions.ConfirmationCodeDoesntExists;
import com.lucas.profile_ms.domains.profile.exceptions.InvalidDataCreateProfileException;
import com.lucas.profile_ms.domains.profile.exceptions.ProfileAlredyExistsException;
import com.lucas.profile_ms.domains.profile.exceptions.ProfileNotFoundException;
import com.lucas.profile_ms.repositories.ProfileRepository;
import com.lucas.profile_ms.services.IProfileService;
import com.lucas.profile_ms.services.ITokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProfileServiceImpl implements IProfileService {

    private final ProfileRepository profileRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;


    public ProfileServiceImpl(ProfileRepository profileRepository, RedisTemplate<String, Object> redisTemplate, AuthenticationManager authenticationManager, ITokenService tokenService) {
        this.profileRepository = profileRepository;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public String createConfirmation(CreateProfileDto data) {
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

        redisTemplate.opsForValue().set(data.email() + confirmationCode, "valid", 1000);
        profileRepository.save(profile);

        return confirmationCode;
    }

    @Transactional
    @Override
    public Profile confirmCode(ConfirmCodeDto data) {
        var code = data.email() + data.code();

        if(!redisTemplate.hasKey(code)){
            throw new ConfirmationCodeDoesntExists("That code does not exists");
        }

        var profile = profileRepository.findByEmail(data.email());

        profile.get().setType(ProfileType.COMUM);

        profileRepository.save(profile.get());

        redisTemplate.delete(code);

        return profile.get();
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

        return tokenService.generateToken(
                (Profile) auth.getPrincipal()
        );
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
