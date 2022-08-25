package com.learning.users.config;

import com.learning.users.UsersApiApplication;
import com.learning.users.models.entities.User;
import com.learning.users.models.entities.UserInRole;
import com.learning.users.repositories.UserInRoleRepository;
import com.learning.users.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInRoleRepository userInRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional =
                userRepository.findByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            List<UserInRole> userInRoles = userInRoleRepository.findByUser(user);
            String[] roles = userInRoles.stream()
                            .map(r->r.getRole().getName())
                            .toArray(String[]::new);
            return  org.springframework.security.core.userdetails.User
                            .withUsername(user.getUsername())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .roles(roles).build();
        } else {
            throw new UsernameNotFoundException("Username " + username +
                    " not found");
        }
    }

}
