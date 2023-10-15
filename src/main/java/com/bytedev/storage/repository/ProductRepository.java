package com.bytedev.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytedev.storage.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
