package com.lucas.profile_ms.controller.impl;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.profile.dto.CreateProfileDto;
import com.lucas.profile_ms.services.IProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/profiles")
public class ProfileControllerImpl {

    private final IProfileService profileService;

    public ProfileControllerImpl(IProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<String> createProfileConfirmation(@RequestBody CreateProfileDto data){
        var code = profileService.createConfirmation(data);

        return ResponseEntity.ok().body(code);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Profile> confirmCode(@RequestBody ConfirmCodeDto data){
        var profile = profileService.confirmCode(data);

        return ResponseEntity.status(201).body(profile);
    }
}
