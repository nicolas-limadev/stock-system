package com.bytedev.storage.service;

import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.exception.CustomException;
import com.bytedev.storage.exception.CustomNotFoundException;
import com.bytedev.storage.repository.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private StorageService storageService;

    private Storage storage1;
    private Storage storage2;
    private StorageDTO storageDTO;

    @BeforeEach
    void setUp() {
        // Setup test data
        storage1 = new Storage();
        storage1.setId(1L);
        storage1.setName("Main Warehouse");
        storage1.setProductStorages(new ArrayList<>());

        storage2 = new Storage();
        storage2.setId(2L);
        storage2.setName("Secondary Warehouse");
        storage2.setProductStorages(new ArrayList<>());

        storageDTO = new StorageDTO();
        storageDTO.setName("New Warehouse");
    }

    @Test
    void findAll_ShouldReturnAllStorages() {
        // Arrange
        List<Storage> storages = Arrays.asList(storage1, storage2);
        when(storageRepository.findAll()).thenReturn(storages);

        // Act
        List<StorageDTO> result = storageService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Main Warehouse", result.get(0).getName());
        assertEquals("Secondary Warehouse", result.get(1).getName());
        verify(storageRepository, times(1)).findAll();
    }

    @Test
    void findAll_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(storageRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> storageService.findAll());
        assertTrue(exception.getMessage().contains("Error fetching storages"));
        verify(storageRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenStorageExists_ShouldReturnStorage() {
        // Arrange
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage1));

        // Act
        StorageDTO result = storageService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Main Warehouse", result.getName());
        verify(storageRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenStorageDoesNotExist_ShouldThrowCustomNotFoundException() {
        // Arrange
        when(storageRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> storageService.findById(99L));
        assertEquals("Storage not found with id: 99", exception.getMessage());
        verify(storageRepository, times(1)).findById(99L);
    }

    @Test
    void create_ShouldReturnCreatedStorage() {
        // Arrange
        Storage newStorage = storageDTO.toEntity();
        Storage savedStorage = new Storage();
        savedStorage.setId(3L);
        savedStorage.setName(newStorage.getName());
        savedStorage.setProductStorages(new ArrayList<>());
        
        when(storageRepository.save(any(Storage.class))).thenReturn(savedStorage);

        // Act
        StorageDTO result = storageService.create(storageDTO);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New Warehouse", result.getName());
        verify(storageRepository, times(1)).save(any(Storage.class));
    }

    @Test
    void create_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(storageRepository.save(any(Storage.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> storageService.create(storageDTO));
        assertTrue(exception.getMessage().contains("Error creating storage"));
        verify(storageRepository, times(1)).save(any(Storage.class));
    }

    @Test
    void update_WhenStorageExists_ShouldReturnUpdatedStorage() {
        // Arrange
        StorageDTO updateDTO = new StorageDTO();
        updateDTO.setName("Updated Warehouse");
        
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage1));
        
        Storage updatedStorage = new Storage();
        updatedStorage.setId(1L);
        updatedStorage.setName("Updated Warehouse");
        updatedStorage.setProductStorages(new ArrayList<>());
        
        when(storageRepository.save(any(Storage.class))).thenReturn(updatedStorage);

        // Act
        StorageDTO result = storageService.update(1L, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Warehouse", result.getName());
        verify(storageRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).save(any(Storage.class));
    }

    @Test
    void update_WhenStorageDoesNotExist_ShouldThrowCustomNotFoundException() {
        // Arrange
        when(storageRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> storageService.update(99L, storageDTO));
        assertEquals("Storage not found with id: 99", exception.getMessage());
        verify(storageRepository, times(1)).findById(99L);
        verify(storageRepository, never()).save(any(Storage.class));
    }

    @Test
    void update_WhenOtherExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage1));
        when(storageRepository.save(any(Storage.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> storageService.update(1L, storageDTO));
        assertTrue(exception.getMessage().contains("Error updating storage"));
        verify(storageRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).save(any(Storage.class));
    }

    @Test
    void delete_WhenStorageExists_ShouldDeleteStorage() {
        // Arrange
        when(storageRepository.existsById(1L)).thenReturn(true);
        doNothing().when(storageRepository).deleteById(1L);

        // Act
        storageService.delete(1L);

        // Assert
        verify(storageRepository, times(1)).existsById(1L);
        verify(storageRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenStorageDoesNotExist_ShouldThrowCustomNotFoundException() {
        // Arrange
        when(storageRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> storageService.delete(99L));
        assertEquals("Storage not found with id: 99", exception.getMessage());
        verify(storageRepository, times(1)).existsById(99L);
        verify(storageRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(storageRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(storageRepository).deleteById(1L);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> storageService.delete(1L));
        assertTrue(exception.getMessage().contains("Error deleting storage"));
        verify(storageRepository, times(1)).existsById(1L);
        verify(storageRepository, times(1)).deleteById(1L);
    }
}