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
import com.bytedev.storage.exception.CustomException;
import com.bytedev.storage.exception.CustomNotFoundException;
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

        public enum QuantityOperation {
                ADD, // Adiciona à quantidade existente
                SET // Define uma nova quantidade
        }
        public List<ProductStorageDTO> findAllProductStorageRecords() {
                return productStorageRepository.findAll().stream()
                                .map(ProductStorageDTO::new)
                                .collect(Collectors.toList());
        }

        public List<ProductStorageDTO> findProductsByStorageId(Long ProductStorageId) {
                Storage storage = storageRepository.findById(ProductStorageId)
                                .orElseThrow(() -> new RuntimeException("Product in Storage not found: " + ProductStorageId));

                return storage.getProductStorages().stream()
                                .map(ProductStorageDTO::new)
                                .collect(Collectors.toList());
        }

        public ProductStorageDTO manageProductQuantity(
                        Long productId,
                        Long storageId,
                        Integer quantity,
                        QuantityOperation operation) {
                try {
                        if (quantity < 0) {
                                throw new Exception("A quantidade não pode ser negativa");
                        }

                        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new CustomNotFoundException(
                                                        "Produto não encontrado: " + productId));

                        Storage storage = storageRepository.findById(storageId)
                                        .orElseThrow(() -> new CustomNotFoundException(
                                                        "Storage não encontrado: " + storageId));

                        Optional<ProductStorage> existingProductStorage = productStorageRepository
                                        .findByProductAndStorage(product, storage);

                        ProductStorage productStorage;
                        if (existingProductStorage.isPresent()) {
                                productStorage = existingProductStorage.get();
                                int newQuantity = operation == QuantityOperation.ADD
                                                ? productStorage.getQuantity() + quantity
                                                : quantity;
                                productStorage.setQuantity(newQuantity);
                        } else {
                                productStorage = new ProductStorage();
                                productStorage.setProduct(product);
                                productStorage.setStorage(storage);
                                productStorage.setQuantity(quantity);
                        }

                        return new ProductStorageDTO(productStorageRepository.save(productStorage));
                } catch (Exception e) {
                        throw new CustomException("Erro ao gerenciar quantidade do produto no storage", e);
                }
        }

        public ProductStorageDTO addProductQuantity(Long productId, Long storageId, Integer quantity) {
                return manageProductQuantity(productId, storageId, quantity, QuantityOperation.ADD);
        }

        public ProductStorageDTO setProductQuantity(Long productId, Long storageId, Integer quantity) {
                return manageProductQuantity(productId, storageId, quantity, QuantityOperation.SET);
        }

        public void deleteProductFromStorage(Long ProductStorageId) {
                productStorageRepository.deleteById(ProductStorageId);
        }

}
