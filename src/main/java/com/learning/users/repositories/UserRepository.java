package com.learning.users.repositories;

import com.learning.users.models.entities.Profile;
import com.learning.users.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String username);

    @Query("SELECT u.username FROM User u")
    public Page<String> findUsernames(Pageable pageable);

}
