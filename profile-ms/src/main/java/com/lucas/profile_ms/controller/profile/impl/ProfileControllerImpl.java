package com.lucas.profile_ms.controller.profile.impl;

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
    public ResponseEntity<Profile> createProfileConfirmation(@RequestBody CreateProfileDto data){
        var profile = profileService.createConfirmation(data);

        return ResponseEntity.ok().body(profile);
    }

    @GetMapping("/confirm/{code}/{email}")
    public ResponseEntity<String> confirmCode(@PathVariable("code") String code, @PathVariable("email") String email){
        profileService.confirmCode(new ConfirmCodeDto(email,code));

        return ResponseEntity.status(201).body("Codigo confirmado com sucesso!");
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
