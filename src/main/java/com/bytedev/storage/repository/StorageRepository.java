package com.bytedev.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytedev.storage.domain.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long>{

    Optional<Storage> findByName(String name);
    boolean existsByName(String name);
}
