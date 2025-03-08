package com.lucas.profile_ms.services.impl;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.profile.dto.CreateProfileDto;
import com.lucas.profile_ms.domains.profile.dto.DeleteProfileDto;
import com.lucas.profile_ms.domains.profile.exceptions.InvalidDataCreateProfileException;
import com.lucas.profile_ms.domains.profile.exceptions.ProfileAlredyExistsException;
import com.lucas.profile_ms.repositories.ProfileRepository;
import com.lucas.profile_ms.services.IProfileService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements IProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    @Override
    public Profile create(CreateProfileDto data) {
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
                .password(data.password())
                .username(data.username())
                .createdAt(LocalDateTime.now())
                .build();

        return profileRepository.save(profile);
    }

    @Override
    public Profile confirmCode(ConfirmCodeDto data) {
        return null;
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
}
