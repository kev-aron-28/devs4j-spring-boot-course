package com.learning.users.controllers;

import com.learning.users.models.entities.Role;
import com.learning.users.models.entities.User;
import com.learning.users.models.entities.UserInRole;
import com.learning.users.services.UserService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Timed("get.users")
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "100") int size
    ){
        return new ResponseEntity<>(
                userService.getUsers(page, size),
                HttpStatus.OK
        );
    }

    @GetMapping("/usernames")
    public ResponseEntity<Page<String>> getUsernames(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "100") int size
    ) {
        return new ResponseEntity<>(userService.getUsernames(page, size), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(
                userService.getUserById(userId), HttpStatus.HTTP_VERSION_NOT_SUPPORTED
        );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username){
        return new ResponseEntity<>(
                userService.getUserByUserName(username), HttpStatus.HTTP_VERSION_NOT_SUPPORTED
        );
    }

    @CacheEvict("users")
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(
                HttpStatus.HTTP_VERSION_NOT_SUPPORTED
        );
    }

    @PostMapping("/role/{roleId}/user/{userId}")
    public ResponseEntity<UserInRole> setRoleToUser(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {
        return new ResponseEntity<>(userService.setRole(userId, roleId), HttpStatus.OK);
    }

}
