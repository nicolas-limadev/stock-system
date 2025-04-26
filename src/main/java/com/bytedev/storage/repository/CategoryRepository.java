package com.bytedev.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytedev.storage.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    Optional<Category> findByName(String name);
}
