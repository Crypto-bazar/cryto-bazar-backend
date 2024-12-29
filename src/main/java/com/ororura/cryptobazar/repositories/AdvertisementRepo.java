package com.ororura.cryptobazar.repositories;

import com.ororura.cryptobazar.entities.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepo extends JpaRepository<Advertisement, Long> {
    Optional<Advertisement> findFirstById(Integer id);
    void deleteById(Integer id);
}
