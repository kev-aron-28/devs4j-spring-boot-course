package com.learning.users.controllers;

import com.learning.users.models.entities.Role;
import com.learning.users.models.entities.User;
import com.learning.users.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(
                roleService.getRoles(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(
                roleService.craeteRole(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(
            @PathVariable("roleId") Integer id,
            @RequestBody Role role
    ) {
        return new ResponseEntity<>(
                roleService.updateRole(id, role),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId") Integer id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{roleName}/users")
    public ResponseEntity<List<User>> getUsersByRole(
            @PathVariable("roleName") String roleName
    ) {
        return new ResponseEntity<>(roleService.getUsersByRole(
                roleName
        ), HttpStatus.ACCEPTED);
    }
}
