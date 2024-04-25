package com.bytedev.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytedev.storage.domain.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long>{
    
}
