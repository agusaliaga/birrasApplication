package com.santander.birras.repositories;

import com.santander.birras.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
}

