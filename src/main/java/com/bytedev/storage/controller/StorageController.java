package com.bytedev.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.service.StorageService;

import lombok.AllArgsConstructor;

import java.util.List;

@RequestMapping("/storages")
@AllArgsConstructor
@RestController
public class StorageController {

    @Autowired
    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<List<StorageDTO>> findAll() {
        return ResponseEntity.ok(storageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageDTO> findById(@PathVariable Long id) {
        StorageDTO storageDTO = storageService.findById(id);
        if (storageDTO != null) {
            return ResponseEntity.ok(storageDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StorageDTO> create(@Validated @RequestBody StorageDTO storageDTO) {
        StorageDTO storage = storageService.create(storageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(storage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageDTO> update(@PathVariable Long id, @RequestBody StorageDTO storageDTO) {
        StorageDTO updatedStorage = storageService.update(id, storageDTO);
        return ResponseEntity.ok(updatedStorage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        storageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

