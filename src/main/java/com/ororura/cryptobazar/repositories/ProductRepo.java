package com.ororura.cryptobazar.repositories;

import com.ororura.cryptobazar.entities.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findFirstById(Integer id);
}
