package com.bytedev.storage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.service.StorageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

@RequestMapping("/v1/storages")
@AllArgsConstructor
@RestController
public class StorageController {

    private final StorageService storageService;
    private static final String ID_PATH = "/{id}";

    @GetMapping
    public ResponseEntity<List<StorageDTO>> findAll() {
        return ResponseEntity.ok(storageService.findAll());
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<StorageDTO> findById(@PathVariable Long id) {
        StorageDTO storageDTO = storageService.findById(id);
        if (storageDTO != null) {
            return ResponseEntity.ok(storageDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StorageDTO> create(@Valid @RequestBody StorageDTO storageDTO) {
        StorageDTO storage = storageService.create(storageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(storage);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<StorageDTO> update(@PathVariable Long id, @RequestBody @Valid StorageDTO storageDTO) {
        StorageDTO updatedStorage = storageService.update(id, storageDTO);
        return ResponseEntity.ok(updatedStorage);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        storageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

