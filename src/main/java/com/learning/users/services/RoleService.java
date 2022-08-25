package com.learning.users.services;

import com.learning.users.models.entities.Role;
import com.learning.users.models.entities.User;
import com.learning.users.repositories.RoleRepository;
import com.learning.users.repositories.UserInRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;

    public List<User> getUsersByRole(String roleName) {
        return userInRoleRepository.findByUsersByRoleName(roleName);
    }

    public List<Role> getRoles() {
        return repository.findAll();
    }

    public Role craeteRole(Role role) {
        return repository.save(role);
    }

    public Role updateRole(Integer id, Role role) {
        Optional<Role> result = repository.findById(id);
        if(result.isPresent()) {
            return repository.save(role);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
    }

    public void deleteRole(Integer id) {
        Optional<Role> result = repository.findById(id);
        result.ifPresent(role -> repository.delete(role));

    }
}
