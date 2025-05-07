package com.bytedev.storage.controller;

import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.dto.ProductStorageQuantityDTO;
import com.bytedev.storage.service.ProductStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductStorageController.class)
class ProductStorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductStorageService productStorageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllProductStorageRecords_ShouldReturnListOfProductStorages() throws Exception {
        // Arrange
        ProductStorageDTO record1 = createProductStorageDTO(1L, 1L, "Product 1", 1L, "Storage 1", 10);
        ProductStorageDTO record2 = createProductStorageDTO(2L, 2L, "Product 2", 1L, "Storage 1", 20);
        List<ProductStorageDTO> records = Arrays.asList(record1, record2);
        
        when(productStorageService.findAllProductStorageRecords()).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/v1/products-storages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].productName").value("Product 1"))
                .andExpect(jsonPath("$[0].storageName").value("Storage 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].productName").value("Product 2"));
    }

    @Test
    void findProductsInStorage_ShouldReturnProductsInSpecificStorage() throws Exception {
        // Arrange
        Long storageId = 1L;
        ProductStorageDTO record1 = createProductStorageDTO(1L, 1L, "Product 1", storageId, "Storage 1", 10);
        ProductStorageDTO record2 = createProductStorageDTO(2L, 2L, "Product 2", storageId, "Storage 1", 20);
        List<ProductStorageDTO> records = Arrays.asList(record1, record2);
        
        when(productStorageService.findProductsByStorageId(storageId)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/v1/products-storages/" + storageId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].storageId").value(storageId))
                .andExpect(jsonPath("$[1].storageId").value(storageId));
    }

    @Test
    void addProductToStorage_ShouldReturnCreatedProductStorage() throws Exception {
        // Arrange
        ProductStorageQuantityDTO requestDto = new ProductStorageQuantityDTO(1L, 1L, 15);
        ProductStorageDTO responseDto = createProductStorageDTO(1L, 1L, "Product 1", 1L, "Storage 1", 15);
        
        when(productStorageService.addProductQuantity(eq(1L), eq(1L), eq(15))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/v1/products-storages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.storageId").value(1))
                .andExpect(jsonPath("$.quantity").value(15));
    }

    @Test
    void updateProductQuantityInStorage_ShouldReturnUpdatedProductStorage() throws Exception {
        // Arrange
        ProductStorageQuantityDTO requestDto = new ProductStorageQuantityDTO(1L, 1L, 25);
        ProductStorageDTO responseDto = createProductStorageDTO(1L, 1L, "Product 1", 1L, "Storage 1", 25);
        
        when(productStorageService.setProductQuantity(eq(1L), eq(1L), eq(25))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(put("/v1/products-storages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.storageId").value(1))
                .andExpect(jsonPath("$.quantity").value(25));
    }

    @Test
    void deleteProductFromStorage_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long productStorageId = 1L;
        doNothing().when(productStorageService).deleteProductFromStorage(productStorageId);

        // Act & Assert
        mockMvc.perform(delete("/v1/products-storages/" + productStorageId))
                .andExpect(status().isNoContent());
        
        verify(productStorageService, times(1)).deleteProductFromStorage(productStorageId);
    }
    
    private ProductStorageDTO createProductStorageDTO(Long id, Long productId, String productName, 
                                                     Long storageId, String storageName, Integer quantity) {
        ProductStorageDTO dto = new ProductStorageDTO();
        dto.setId(id);
        dto.setProductId(productId);
        dto.setProductName(productName);
        dto.setStorageId(storageId);
        dto.setStorageName(storageName);
        dto.setQuantity(quantity);
        return dto;
    }
}