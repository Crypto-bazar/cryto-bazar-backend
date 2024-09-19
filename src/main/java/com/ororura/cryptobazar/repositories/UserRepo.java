package com.ororura.cryptobazar.repositories;

import com.ororura.cryptobazar.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByFirstName(String username);
    Optional<UserEntity> findByEmail(String email);
}
