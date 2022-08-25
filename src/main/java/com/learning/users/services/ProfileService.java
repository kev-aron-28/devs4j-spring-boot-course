package com.learning.users.services;

import com.learning.users.models.entities.Profile;
import com.learning.users.models.entities.User;
import com.learning.users.repositories.ProfileRepository;
import com.learning.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;
    public Profile create(Integer userId, Profile profile) {
        Optional<User> result = userRepository.findById(userId);
        if(result.isPresent()){
            profile.setUser(result.get());
            return profileRepository.save(profile);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public Profile getByUserIdAndProfileId(Integer userId, Integer profileId) {
        return profileRepository
                .findByUserIdAndProfileId(userId, profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile or user not found"));
    }
}
