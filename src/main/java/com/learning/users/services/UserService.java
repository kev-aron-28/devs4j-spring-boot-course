package com.learning.users.services;
import com.learning.users.models.entities.Role;
import com.learning.users.models.entities.User;
import com.learning.users.models.entities.UserInRole;
import com.learning.users.repositories.RoleRepository;
import com.learning.users.repositories.UserInRoleRepository;
import com.learning.users.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    public Page<User> getUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(
                () ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Unable to find user"
                        )
        );
    }

    @Cacheable("users")
    public User getUserByUserName(String username) {
        log.info("Getting user by username");
        return userRepository.findByUsername(username).orElseThrow(
                () ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Unable to find user"
                        )
        );
    }

    public void deleteUser(String username) {
        User userByUserName = getUserByUserName(username);
        userRepository.delete(userByUserName);
    }
    public Page<String> getUsernames(int page, int size) {
        return userRepository.findUsernames(PageRequest.of(page, size));
    }

    public UserInRole setRole(Integer userId, Integer roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        Optional<User> user = userRepository.findById(userId);
        if(!role.isPresent() && !user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User or role not found");
        }
        UserInRole userInRole = new UserInRole(
                user.get(),
                role.get()
        );

        return userInRoleRepository.save(userInRole);
    }
}
