package com.bytedev.storage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.repository.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    public List<StorageDTO> listStorages() {
        return storageRepository.findAll().stream()
                .map(StorageDTO::new)
                .collect(Collectors.toList());
    }

    public StorageDTO getStorageById(Long id) {
        return storageRepository.findById(id)
                .map(StorageDTO::new)
                .orElse(null);
    }

    public StorageDTO saveStorage(StorageDTO storageDTO) {
        Storage storage = storageDTO.toEntity();
        Storage savedStorage = storageRepository.save(storage);
        return new StorageDTO(savedStorage);
    }

    public StorageDTO updateStorage(Long id, StorageDTO storageDTO) {
        return storageRepository.findById(id)
                .map(storage -> {
                    storage.setName(storageDTO.getName());
                    Storage updatedStorage = storageRepository.save(storage);
                    return new StorageDTO(updatedStorage);
                })
                .orElse(null);
    }

    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);
    }

    public StorageDTO findByName(String name) {
        return storageRepository.findByName(name)
                .map(StorageDTO::new)
                .orElseThrow(() -> new RuntimeException("Storage not found: " + name));
    }

    public boolean existsByName(String name) {
        return storageRepository.existsByName(name);
    }
}
