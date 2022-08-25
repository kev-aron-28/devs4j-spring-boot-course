package com.learning.users.controllers;

import com.learning.users.models.entities.Profile;
import com.learning.users.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/users/{userId}/profiles")
@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfiles(
            @PathVariable("userId") Integer userId,
            @PathVariable("profileId") Integer profileId
            ) {
        return new ResponseEntity<>(profileService.getByUserIdAndProfileId(userId, profileId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Profile> create(@PathVariable("userId") Integer userId, @RequestBody Profile profile) {
        return new ResponseEntity<>(profileService.create(userId, profile), HttpStatus.CREATED);
    }
}
