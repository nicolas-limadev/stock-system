package com.bytedev.storage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.repository.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {

    @Autowired
    private final StorageRepository storageRepository;

    public List<StorageDTO> findAll() {
        return storageRepository.findAll().stream()
                .map(StorageDTO::new)
                .collect(Collectors.toList());
    }

    public StorageDTO findById(Long id) {
        return storageRepository.findById(id)
                .map(StorageDTO::new)
                .orElse(null);
    }

    public StorageDTO create(StorageDTO storageDTO) {
        Storage storage = storageDTO.toEntity();
        Storage savedStorage = storageRepository.save(storage);
        return new StorageDTO(savedStorage);
    }

    public StorageDTO update(Long id, StorageDTO storageDTO) {
        return storageRepository.findById(id)
                .map(storage -> {
                    storage.setName(storageDTO.getName());
                    Storage updatedStorage = storageRepository.save(storage);
                    return new StorageDTO(updatedStorage);
                })
                .orElse(null);
    }
    
    public void delete(Long id) {
        storageRepository.deleteById(id);
    }
}
