package com.ororura.cryptobazar.repositories;

import com.ororura.cryptobazar.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByLogin(String login);
    Optional<Person> findByEmail(String email);
}
