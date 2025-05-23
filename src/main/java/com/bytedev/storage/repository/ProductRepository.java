package com.bytedev.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytedev.storage.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findByName(String name);
}
