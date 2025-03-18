package com.lucas.profile_ms.controller.impl;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.profile.dto.AuthProfileDto;
import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.profile.dto.CreateProfileDto;
import com.lucas.profile_ms.services.IProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/confirm")
    public ResponseEntity<Profile> confirmCode(@RequestBody ConfirmCodeDto data){
        var profile = profileService.confirmCode(data);

        return ResponseEntity.status(201).body(profile);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody AuthProfileDto data){
        var token = profileService.auth(data);

        return ResponseEntity.ok().body(token);
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAll(){
        return ResponseEntity.ok().body(profileService.getAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getEmail(@PathVariable("userId")String userId){
        var email = profileService.getEmail(userId);

        return ResponseEntity.ok().body(email);
    }
}
