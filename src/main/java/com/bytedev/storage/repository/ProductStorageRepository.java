package com.bytedev.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytedev.storage.domain.Product;
import com.bytedev.storage.domain.ProductStorage;
import com.bytedev.storage.domain.Storage;

@Repository
public interface ProductStorageRepository extends JpaRepository<ProductStorage, Long> {
    Optional<ProductStorage> findByProductAndStorage(Product productId, Storage storageId);
}
