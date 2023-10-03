package com.bytedev.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytedev.stock.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
