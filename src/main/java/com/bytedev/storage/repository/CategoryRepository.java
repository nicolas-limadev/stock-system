package com.bytedev.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytedev.storage.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    Category findByName(String name);
}
