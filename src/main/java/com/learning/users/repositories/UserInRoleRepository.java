package com.learning.users.repositories;

import com.learning.users.models.entities.User;
import com.learning.users.models.entities.UserInRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInRoleRepository extends JpaRepository<UserInRole, Integer> {

    @Query("SELECT u.user FROM UserInRole u WHERE u.role.name=?1")
    List<User> findByUsersByRoleName(String roleName);

    List<UserInRole> findByUser(User user);
}
