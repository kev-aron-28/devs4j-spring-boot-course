package com.learning.users.repositories;

import com.learning.users.models.entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdressRepository extends CrudRepository<Address, Integer> {

    @Query("SElECT a FROM Address a where a.profile.user.id=?1 AND a.profile.id=?2")
    List<Address> findByProfileId(Integer userId, Integer profileId);

}
