package com.bytedev.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytedev.storage.domain.Storage;

public interface StorageRepository extends JpaRepository<Storage, Long>{
    
}
