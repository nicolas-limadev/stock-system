package com.bytedev.storage.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytedev.storage.domain.Product;
import com.bytedev.storage.domain.ProductStorage;
import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.repository.ProductRepository;
import com.bytedev.storage.repository.ProductStorageRepository;
import com.bytedev.storage.repository.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductStorageService {

        @Autowired
        private final ProductStorageRepository productStorageRepository;
        @Autowired
        private final ProductRepository productRepository;
        @Autowired
        private final StorageRepository storageRepository;

        public List<ProductStorageDTO> getProductsByStorage(Long storageId) {
                Storage storage = storageRepository.findById(storageId)
                                .orElseThrow(() -> new RuntimeException("Storage not found: " + storageId));

                return storage.getProductStorages().stream()
                                .map(ProductStorageDTO::new)
                                .collect(Collectors.toList());
        }

        public List<ProductStorageDTO> getAllProductStorages() {
                return productStorageRepository.findAll().stream()
                                .map(ProductStorageDTO::new)
                                .collect(Collectors.toList());
        }

        public ProductStorageDTO addProductToStorage(Long productId, Long storageId, Integer quantity) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                Storage storage = storageRepository.findById(storageId)
                                .orElseThrow(() -> new RuntimeException("Storage not found: " + storageId));

                Optional<ProductStorage> existingProductStorage = productStorageRepository
                                .findByProductAndStorage(product, storage);

                ProductStorage productStorage;
                if (existingProductStorage.isPresent()) {
                        productStorage = existingProductStorage.get();
                        productStorage.setQuantity(productStorage.getQuantity() + quantity);
                } else {
                        productStorage = new ProductStorage();
                        productStorage.setProduct(product);
                        productStorage.setStorage(storage);
                        productStorage.setQuantity(quantity);
                }

                productStorage = productStorageRepository.save(productStorage);
                return new ProductStorageDTO(productStorage);
        }

        public ProductStorageDTO updateProductQuantity(Long productId, Long storageId, Integer newQuantity) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                Storage storage = storageRepository.findById(storageId)
                                .orElseThrow(() -> new RuntimeException("Storage not found: " + storageId));

                ProductStorage productStorage = productStorageRepository
                                .findByProductAndStorage(product, storage)
                                .orElseThrow(() -> new RuntimeException("Product not found in this storage"));

                productStorage.setQuantity(newQuantity);
                productStorage = productStorageRepository.save(productStorage);

                return new ProductStorageDTO(productStorage);
        }

        public void removeProductFromStorage(Long productId, Long storageId) {
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                Storage storage = storageRepository.findById(storageId)
                                .orElseThrow(() -> new RuntimeException("Storage not found: " + storageId));

                ProductStorage productStorage = productStorageRepository
                                .findByProductAndStorage(product, storage)
                                .orElseThrow(() -> new RuntimeException("Product not found in this storage"));

                productStorageRepository.delete(productStorage);
        }

}
