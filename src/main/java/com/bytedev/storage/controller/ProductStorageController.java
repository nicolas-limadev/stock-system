package com.bytedev.storage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.dto.ProductStorageQuantityDTO;
import com.bytedev.storage.service.ProductStorageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/v1/products-storages")
@AllArgsConstructor
@RestController
public class ProductStorageController {

    private final ProductStorageService productStorageService;
    private static final String ID_PATH = "/{id}";

    @GetMapping
    public ResponseEntity<List<ProductStorageDTO>> findAllProductStorageRecords() {
        List<ProductStorageDTO> products = productStorageService.findAllProductStorageRecords();
        return ResponseEntity.ok(products);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<List<ProductStorageDTO>> findProductsInStorage(@PathVariable Long id) {
        List<ProductStorageDTO> products = productStorageService.findProductsByStorageId(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductStorageDTO> addProductToStorage(@RequestBody @Valid ProductStorageQuantityDTO dto) {
        ProductStorageDTO result = productStorageService.addProductQuantity(
                dto.getProductId(),
                dto.getStorageId(),
                dto.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping
    public ResponseEntity<ProductStorageDTO> updateProductQuantityInStorage(
            @RequestBody ProductStorageQuantityDTO dto) {
        ProductStorageDTO result = productStorageService.setProductQuantity(
                dto.getProductId(),
                dto.getStorageId(),
                dto.getQuantity());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<Void> deleteProductFromStorage(@PathVariable Long id) {
        productStorageService.deleteProductFromStorage(id);
        return ResponseEntity.noContent().build();
    }
}
