package com.bytedev.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytedev.stock.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    Category findByName(String name);
}
