package com.bytedev.storage.controller;

import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.service.StorageService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StorageController.class)
class StorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageService storageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_ShouldReturnListOfStorages() throws Exception {
        // Arrange
        StorageDTO storage1 = new StorageDTO();
        StorageDTO storage2 = new StorageDTO();
        List<StorageDTO> storages = Arrays.asList(storage1, storage2);
        
        when(storageService.findAll()).thenReturn(storages);

        // Act & Assert
        mockMvc.perform(get("/v1/storages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findById_WhenStorageExists_ShouldReturnStorage() throws Exception {
        // Arrange
        StorageDTO storage = new StorageDTO();
        when(storageService.findById(1L)).thenReturn(storage);

        // Act & Assert
        mockMvc.perform(get("/v1/storages/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findById_WhenStorageDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(storageService.findById(1L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/v1/storages/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_ShouldReturnCreatedStorage() throws Exception {
        // Arrange
        StorageDTO storageToCreate = new StorageDTO();
        StorageDTO createdStorage = new StorageDTO();
        
        when(storageService.create(any(StorageDTO.class))).thenReturn(createdStorage);

        // Act & Assert
        mockMvc.perform(post("/v1/storages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_ShouldReturnUpdatedStorage() throws Exception {
        // Arrange
        StorageDTO storageToUpdate = new StorageDTO();
        StorageDTO updatedStorage = new StorageDTO();
        
        when(storageService.update(eq(1L), any(StorageDTO.class))).thenReturn(updatedStorage);

        // Act & Assert
        mockMvc.perform(put("/v1/storages/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageToUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(storageService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/v1/storages/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void create_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        StorageDTO invalidStorage = new StorageDTO();
        // Configurar o DTO com dados inválidos aqui

        // Act & Assert
        mockMvc.perform(post("/v1/storages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStorage)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        StorageDTO invalidStorage = new StorageDTO();
        // Configurar o DTO com dados inválidos aqui

        // Act & Assert
        mockMvc.perform(put("/v1/storages/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStorage)))
                .andExpect(status().isBadRequest());
    }
}
