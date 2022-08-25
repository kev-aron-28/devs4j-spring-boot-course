package com.learning.users.services;

import com.learning.users.models.entities.Address;
import com.learning.users.models.entities.Profile;
import com.learning.users.repositories.AdressRepository;
import com.learning.users.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdressService {
    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public List<Address> findAddressByProfileIdAndUserId(Integer userId, Integer profileId) {
        return adressRepository.findByProfileId(userId, profileId);
    }

    public Address createAddress(Integer userId, Integer profileId, Address address) {
        Optional<Profile> result = profileRepository.findByUserIdAndProfileId(userId, profileId);
        if(result.isPresent()) {
            address.setProfile(result.get());
            return adressRepository.save(address);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
        }
    }
}
