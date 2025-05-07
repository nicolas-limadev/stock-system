package com.bytedev.storage.controller;

import com.bytedev.storage.dto.CategoryDTO;
import com.bytedev.storage.service.CategoryService;
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

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_ShouldReturnListOfCategories() throws Exception {
        // Arrange
        CategoryDTO category1 = new CategoryDTO();
        CategoryDTO category2 = new CategoryDTO();
        List<CategoryDTO> categories = Arrays.asList(category1, category2);
        
        when(categoryService.findAll()).thenReturn(categories);

        // Act & Assert
        mockMvc.perform(get("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findById_ShouldReturnCategory() throws Exception {
        // Arrange
        CategoryDTO category = new CategoryDTO();
        when(categoryService.findById(1L)).thenReturn(category);

        // Act & Assert
        mockMvc.perform(get("/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void create_ShouldReturnCreatedCategory() throws Exception {
        // Arrange
        CategoryDTO categoryToCreate = new CategoryDTO();
        CategoryDTO createdCategory = new CategoryDTO();
        
        when(categoryService.create(any(CategoryDTO.class))).thenReturn(createdCategory);

        // Act & Assert
        mockMvc.perform(post("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void update_ShouldReturnUpdatedCategory() throws Exception {
        // Arrange
        CategoryDTO categoryToUpdate = new CategoryDTO();
        CategoryDTO updatedCategory = new CategoryDTO();
        
        when(categoryService.update(eq(1L), any(CategoryDTO.class))).thenReturn(updatedCategory);

        // Act & Assert
        mockMvc.perform(put("/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryToUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(categoryService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/v1/categories/1"))
                .andExpect(status().isNoContent());
    }
}
