package com.bytedev.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.service.StorageService;

import java.util.List;

@RequestMapping("/storages")
@RestController
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<List<StorageDTO>> listStorage() {
        return ResponseEntity.ok(storageService.listStorages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageDTO> getStorageById(@PathVariable Long id) {
        StorageDTO storageDTO = storageService.getStorageById(id);
        if (storageDTO != null) {
            return ResponseEntity.ok(storageDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StorageDTO> createStorage(@Validated @RequestBody StorageDTO storageDTO) {
        StorageDTO storage = storageService.saveStorage(storageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(storage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageDTO> updateStorage(@PathVariable Long id, @RequestBody StorageDTO storageDTO) {
        StorageDTO updatedStorage = storageService.updateStorage(id, storageDTO);
        return ResponseEntity.ok(updatedStorage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorage(@PathVariable Long id) {
        storageService.deleteStorage(id);
        return ResponseEntity.noContent().build();
    }
}

