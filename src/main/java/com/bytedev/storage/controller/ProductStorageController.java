package com.bytedev.storage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bytedev.storage.dto.AddProductToStorageDTO;
import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.dto.UpdateProductQuantityDTO;
import com.bytedev.storage.service.ProductStorageService;

import lombok.AllArgsConstructor;

@RequestMapping("/products-storages")
@AllArgsConstructor
@RestController
public class ProductStorageController {

    @Autowired
    private final ProductStorageService productStorageService;

    @GetMapping
    public ResponseEntity<List<ProductStorageDTO>> getAllProductStorages() {
        List<ProductStorageDTO> products = productStorageService.getAllProductStorages();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{storageId}")
    public ResponseEntity<List<ProductStorageDTO>> getProductsByStorage(@PathVariable Long storageId) {
        List<ProductStorageDTO> products = productStorageService.getProductsByStorage(storageId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductStorageDTO> addProductToStorage(@RequestBody AddProductToStorageDTO dto) {
        ProductStorageDTO result = productStorageService.addProductToStorage(
                dto.getProductId(),
                dto.getStorageId(),
                dto.getQuantity());
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<ProductStorageDTO> updateQuantity(@RequestBody UpdateProductQuantityDTO dto) {
        ProductStorageDTO result = productStorageService.updateProductQuantity(
                dto.getProductId(),
                dto.getStorageId(),
                dto.getNewQuantity());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeProductFromStorage(
            @PathVariable Long productId,
            @PathVariable Long storageId) {
        productStorageService.removeProductFromStorage(productId, storageId);
        return ResponseEntity.noContent().build();
    }
}
