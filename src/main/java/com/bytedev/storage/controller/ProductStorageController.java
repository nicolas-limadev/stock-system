package com.bytedev.storage.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytedev.storage.dto.AddProductToStorageDTO;
import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.dto.UpdateProductQuantityDTO;
import com.bytedev.storage.service.ProductStorageService;

import lombok.AllArgsConstructor;

@RequestMapping("/product-storage")
@AllArgsConstructor
@RestController
public class ProductStorageController {
    
    private final ProductStorageService productStorageService;

    @PostMapping("/add")
    public ResponseEntity<ProductStorageDTO> addProductToStorage(@RequestBody AddProductToStorageDTO dto) {
        ProductStorageDTO result = productStorageService.addProductToStorage(
            dto.getProductId(), 
            dto.getStorageId(), 
            dto.getQuantity()
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/storage/{storageId}")
    public ResponseEntity<List<ProductStorageDTO>> getProductsByStorage(@PathVariable Long storageId) {
        List<ProductStorageDTO> products = productStorageService.getProductsByStorage(storageId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<ProductStorageDTO> updateQuantity(@RequestBody UpdateProductQuantityDTO dto) {
        ProductStorageDTO result = productStorageService.updateProductQuantity(
            dto.getProductId(),
            dto.getStorageId(),
            dto.getNewQuantity()
        );
        return ResponseEntity.ok(result);
    }
}
