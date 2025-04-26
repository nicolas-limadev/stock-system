package com.bytedev.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.service.StorageService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RequestMapping("/storages")
@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping
    public List<StorageDTO> listStorage() {
        return storageService.listStorages();

    }

    @GetMapping("/{id}")
    public StorageDTO getStorageById(Long id) {
        return storageService.getStorageById(id);
    }

    @PostMapping()
    public ResponseEntity<StorageDTO> createStorage(@Validated @RequestBody StorageDTO storageDTO) {
        StorageDTO storage = storageService.saveStorage(storageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(storage);
    }

    @PutMapping("/{id}")
    public StorageDTO updateStorage(Long id, StorageDTO storageDTO) {
        return storageService.updateStorage(id, storageDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStorage(Long id) {

        storageService.deleteStorage(id);
    }
}
