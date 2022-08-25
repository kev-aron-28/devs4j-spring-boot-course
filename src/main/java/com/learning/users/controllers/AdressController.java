package com.learning.users.controllers;


import com.learning.users.models.entities.Address;
import com.learning.users.services.AdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/profiles/{profileId}/adresses")
public class AdressController {

    @Autowired
    private AdressService adressService;

    @GetMapping
    public ResponseEntity<List<Address>> findAddressByProfile(
            @PathVariable("userId") Integer userId,
            @PathVariable("profileId") Integer profileId
    ) {
        return new ResponseEntity<>(
                adressService.findAddressByProfileIdAndUserId(userId, profileId),
                HttpStatus.OK
                );
    }

    @PostMapping
    public ResponseEntity<Address> create(
            @RequestBody Address address,
            @PathVariable("userId") Integer userId,
            @PathVariable("profileId") Integer profileId
    ) {
        return new ResponseEntity<>(adressService.createAddress(
                userId,
                profileId,
                address
        ), HttpStatus.CREATED);
    }
}
