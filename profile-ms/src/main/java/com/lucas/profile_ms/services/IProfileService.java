package com.lucas.profile_ms.services;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.AuthProfileDto;
import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.profile.dto.CreateProfileDto;
import com.lucas.profile_ms.domains.profile.dto.DeleteProfileDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IProfileService {

    String createConfirmation(CreateProfileDto data);
    Profile confirmCode(ConfirmCodeDto data);
    void delete(DeleteProfileDto data);
    Profile findById(String id);
    List<Profile> getAll();
    UserDetails findByEmailDetails(String username);
    String auth(AuthProfileDto data);
    String getEmail(String userId);

}
