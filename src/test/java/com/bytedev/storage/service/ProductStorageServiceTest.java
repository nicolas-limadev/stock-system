package com.bytedev.storage.service;

import com.bytedev.storage.domain.Product;
import com.bytedev.storage.domain.ProductStorage;
import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.exception.CustomException;
import com.bytedev.storage.exception.CustomNotFoundException;
import com.bytedev.storage.repository.ProductRepository;
import com.bytedev.storage.repository.ProductStorageRepository;
import com.bytedev.storage.repository.StorageRepository;
import com.bytedev.storage.service.ProductStorageService.QuantityOperation;
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
class ProductStorageServiceTest {

    @Mock
    private ProductStorageRepository productStorageRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private ProductStorageService productStorageService;

    private Product product;
    private Storage storage;
    private ProductStorage productStorage;

    @BeforeEach
    void setUp() {
        // Setup test data
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setProductStorages(new ArrayList<>());

        storage = new Storage();
        storage.setId(1L);
        storage.setName("Test Storage");
        
        productStorage = new ProductStorage();
        productStorage.setId(1L);
        productStorage.setProduct(product);
        productStorage.setStorage(storage);
        productStorage.setQuantity(10);
        
        List<ProductStorage> productStorages = new ArrayList<>();
        productStorages.add(productStorage);
        storage.setProductStorages(productStorages);
        product.setProductStorages(productStorages);
    }

    @Test
    void findAllProductStorageRecords_ShouldReturnAllRecords() {
        // Arrange
        List<ProductStorage> productStorages = Arrays.asList(productStorage);
        when(productStorageRepository.findAll()).thenReturn(productStorages);

        // Act
        List<ProductStorageDTO> result = productStorageService.findAllProductStorageRecords();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getProductId());
        assertEquals("Test Product", result.get(0).getProductName());
        assertEquals(1L, result.get(0).getStorageId());
        assertEquals("Test Storage", result.get(0).getStorageName());
        assertEquals(10, result.get(0).getQuantity());
        verify(productStorageRepository, times(1)).findAll();
    }

    @Test
    void findAllProductStorageRecords_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(productStorageRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> productStorageService.findAllProductStorageRecords());
        assertTrue(exception.getMessage().contains("Error fetching products in storages"));
        verify(productStorageRepository, times(1)).findAll();
    }

    @Test
    void findProductsByStorageId_WhenStorageExists_ShouldReturnProducts() {
        // Arrange
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));

        // Act
        List<ProductStorageDTO> result = productStorageService.findProductsByStorageId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getProductId());
        assertEquals("Test Product", result.get(0).getProductName());
        verify(storageRepository, times(1)).findById(1L);
    }

    @Test
    void findProductsByStorageId_WhenStorageDoesNotExist_ShouldThrowException() {
        // Arrange
        when(storageRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productStorageService.findProductsByStorageId(99L));
        assertEquals("Product in Storage not found: 99", exception.getMessage());
        verify(storageRepository, times(1)).findById(99L);
    }

    @Test
    void addProductQuantity_WhenProductAndStorageExistAndNoExistingRecord_ShouldAddQuantity() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));
        when(productStorageRepository.findByProductAndStorage(product, storage)).thenReturn(Optional.empty());
        
        ProductStorage newProductStorage = new ProductStorage();
        newProductStorage.setId(2L);
        newProductStorage.setProduct(product);
        newProductStorage.setStorage(storage);
        newProductStorage.setQuantity(5);
        
        when(productStorageRepository.save(any(ProductStorage.class))).thenReturn(newProductStorage);

        // Act
        ProductStorageDTO result = productStorageService.addProductQuantity(1L, 1L, 5);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(1L, result.getStorageId());
        assertEquals(5, result.getQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).findById(1L);
        verify(productStorageRepository, times(1)).findByProductAndStorage(product, storage);
        verify(productStorageRepository, times(1)).save(any(ProductStorage.class));
    }

    @Test
    void addProductQuantity_WhenProductAndStorageExistAndExistingRecord_ShouldAddToExistingQuantity() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));
        when(productStorageRepository.findByProductAndStorage(product, storage)).thenReturn(Optional.of(productStorage));
        
        ProductStorage updatedProductStorage = new ProductStorage();
        updatedProductStorage.setId(1L);
        updatedProductStorage.setProduct(product);
        updatedProductStorage.setStorage(storage);
        updatedProductStorage.setQuantity(15); // 10 + 5
        
        when(productStorageRepository.save(any(ProductStorage.class))).thenReturn(updatedProductStorage);

        // Act
        ProductStorageDTO result = productStorageService.addProductQuantity(1L, 1L, 5);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(1L, result.getStorageId());
        assertEquals(15, result.getQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).findById(1L);
        verify(productStorageRepository, times(1)).findByProductAndStorage(product, storage);
        verify(productStorageRepository, times(1)).save(any(ProductStorage.class));
    }

    @Test
    void setProductQuantity_WhenProductAndStorageExistAndNoExistingRecord_ShouldSetQuantity() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));
        when(productStorageRepository.findByProductAndStorage(product, storage)).thenReturn(Optional.empty());
        
        ProductStorage newProductStorage = new ProductStorage();
        newProductStorage.setId(2L);
        newProductStorage.setProduct(product);
        newProductStorage.setStorage(storage);
        newProductStorage.setQuantity(20);
        
        when(productStorageRepository.save(any(ProductStorage.class))).thenReturn(newProductStorage);

        // Act
        ProductStorageDTO result = productStorageService.setProductQuantity(1L, 1L, 20);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(1L, result.getStorageId());
        assertEquals(20, result.getQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).findById(1L);
        verify(productStorageRepository, times(1)).findByProductAndStorage(product, storage);
        verify(productStorageRepository, times(1)).save(any(ProductStorage.class));
    }

    @Test
    void setProductQuantity_WhenProductAndStorageExistAndExistingRecord_ShouldReplaceQuantity() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));
        when(productStorageRepository.findByProductAndStorage(product, storage)).thenReturn(Optional.of(productStorage));
        
        ProductStorage updatedProductStorage = new ProductStorage();
        updatedProductStorage.setId(1L);
        updatedProductStorage.setProduct(product);
        updatedProductStorage.setStorage(storage);
        updatedProductStorage.setQuantity(20); // Set to 20 regardless of previous value
        
        when(productStorageRepository.save(any(ProductStorage.class))).thenReturn(updatedProductStorage);

        // Act
        ProductStorageDTO result = productStorageService.setProductQuantity(1L, 1L, 20);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(1L, result.getStorageId());
        assertEquals(20, result.getQuantity());
        verify(productRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).findById(1L);
        verify(productStorageRepository, times(1)).findByProductAndStorage(product, storage);
        verify(productStorageRepository, times(1)).save(any(ProductStorage.class));
    }

    @Test
    void manageProductQuantity_WhenNegativeQuantity_ShouldThrowException() {
        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, 
            () -> productStorageService.manageProductQuantity(1L, 1L, -5, QuantityOperation.ADD));
        assertTrue(exception.getMessage().contains("Erro ao gerenciar quantidade do produto no storage"));
        verify(productRepository, never()).findById(anyLong());
        verify(storageRepository, never()).findById(anyLong());
    }

    @Test
    void manageProductQuantity_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, 
            () -> productStorageService.manageProductQuantity(99L, 1L, 5, QuantityOperation.ADD));
        assertTrue(exception.getMessage().contains("Erro ao gerenciar quantidade do produto no storage"));
        verify(productRepository, times(1)).findById(99L);
        verify(storageRepository, never()).findById(anyLong());
    }

    @Test
    void manageProductQuantity_WhenStorageDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(storageRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, 
            () -> productStorageService.manageProductQuantity(1L, 99L, 5, QuantityOperation.ADD));
        assertTrue(exception.getMessage().contains("Erro ao gerenciar quantidade do produto no storage"));
        verify(productRepository, times(1)).findById(1L);
        verify(storageRepository, times(1)).findById(99L);
    }

    @Test
    void deleteProductFromStorage_WhenProductStorageExists_ShouldDelete() {
        // Arrange
        when(productStorageRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productStorageRepository).deleteById(1L);

        // Act
        productStorageService.deleteProductFromStorage(1L);

        // Assert
        verify(productStorageRepository, times(1)).existsById(1L);
        verify(productStorageRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProductFromStorage_WhenProductStorageDoesNotExist_ShouldThrowCustomNotFoundException() {
        // Arrange
        when(productStorageRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, 
            () -> productStorageService.deleteProductFromStorage(99L));
        assertEquals("Product in Storage not found with id: 99", exception.getMessage());
        verify(productStorageRepository, times(1)).existsById(99L);
        verify(productStorageRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteProductFromStorage_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(productStorageRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(productStorageRepository).deleteById(1L);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, 
            () -> productStorageService.deleteProductFromStorage(1L));
        assertTrue(exception.getMessage().contains("Error deleting product in storage"));
        verify(productStorageRepository, times(1)).existsById(1L);
        verify(productStorageRepository, times(1)).deleteById(1L);
    }
}