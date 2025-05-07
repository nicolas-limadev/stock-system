package com.bytedev.storage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.exception.CustomException;
import com.bytedev.storage.exception.CustomNotFoundException;
import com.bytedev.storage.repository.StorageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {

    @Autowired
    private final StorageRepository storageRepository;

    public List<StorageDTO> findAll() {
        try {
            return storageRepository.findAll().stream()
                    .map(StorageDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException("Error fetching storages: " + e.getMessage());
        }
    }

    public StorageDTO findById(Long id) {
        return storageRepository.findById(id)
                .map(StorageDTO::new)
                .orElseThrow(() -> new CustomNotFoundException("Storage not found with id: " + id));
    }

    public StorageDTO create(StorageDTO storageDTO) {
        try {
            Storage storage = storageDTO.toEntity();
            Storage savedStorage = storageRepository.save(storage);
            return new StorageDTO(savedStorage);
        } catch (Exception e) {
            throw new CustomException("Error creating storage: " + e.getMessage());
        }
    }

    public StorageDTO update(Long id, StorageDTO storageDTO) {
        try {
            return storageRepository.findById(id)
                    .map(storage -> {
                        storage.setName(storageDTO.getName());
                        Storage updatedStorage = storageRepository.save(storage);
                        return new StorageDTO(updatedStorage);
                    })
                    .orElseThrow(() -> new CustomNotFoundException("Storage not found with id: " + id));
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error updating storage: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        if (!storageRepository.existsById(id)) {
            throw new CustomNotFoundException("Storage not found with id: " + id);
        }
        try {
            storageRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Error deleting storage: " + e.getMessage());
        }
    }
}
