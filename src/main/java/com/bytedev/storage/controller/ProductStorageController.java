package com.bytedev.storage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.dto.ProductStorageQuantityDTO;
import com.bytedev.storage.service.ProductStorageService;

import lombok.AllArgsConstructor;

@RequestMapping("/products-storages")
@AllArgsConstructor
@RestController
public class ProductStorageController {

    @Autowired
    private final ProductStorageService productStorageService;

    @GetMapping
    public ResponseEntity<List<ProductStorageDTO>> findAllProductStorageRecords() {
        List<ProductStorageDTO> products = productStorageService.findAllProductStorageRecords();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{storageId}")
    public ResponseEntity<List<ProductStorageDTO>> findProductsInStorage(@PathVariable Long storageId) {
        List<ProductStorageDTO> products = productStorageService.findProductsInStorage(storageId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductStorageDTO> addProductToStorage(@RequestBody ProductStorageQuantityDTO dto) {
        ProductStorageDTO result = productStorageService.addProductQuantity(
                dto.getProductId(),
                dto.getStorageId(),
                dto.getQuantity());
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<ProductStorageDTO> updateProductQuantityInStorage(@RequestBody ProductStorageQuantityDTO dto) {
        ProductStorageDTO result = productStorageService.setProductQuantity(
                dto.getProductId(),
                dto.getStorageId(),
                dto.getQuantity());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProductFromStorage(
            @PathVariable Long productId,
            @PathVariable Long storageId) {
        productStorageService.deleteProductFromStorage(productId, storageId);
        return ResponseEntity.noContent().build();
    }
}
