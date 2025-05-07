package com.bytedev.storage.controller;

import com.bytedev.storage.dto.ProductDTO;
import com.bytedev.storage.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_ShouldReturnListOfProducts() throws Exception {
        // Arrange
        ProductDTO product1 = createProductDTO(1L, "Product 1", 10.0);
        ProductDTO product2 = createProductDTO(2L, "Product 2", 20.0);
        List<ProductDTO> products = Arrays.asList(product1, product2);
        
        when(productService.findAll()).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() throws Exception {
        // Arrange
        ProductDTO product = createProductDTO(1L, "Product 1", 10.0);
        when(productService.findById(1L)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(get("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    void findById_WhenProductDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(productService.findById(1L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_ShouldReturnCreatedProduct() throws Exception {
        // Arrange
        ProductDTO productToCreate = createProductDTO(null, "New Product", 15.0);
        ProductDTO createdProduct = createProductDTO(1L, "New Product", 15.0);
        
        when(productService.create(any(ProductDTO.class))).thenReturn(createdProduct);

        // Act & Assert
        mockMvc.perform(post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.price").value(15.0));
    }

    @Test
    void update_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        ProductDTO productToUpdate = createProductDTO(1L, "Updated Product", 25.0);
        
        doNothing().when(productService).update(eq(1L), any(ProductDTO.class));

        // Act & Assert
        mockMvc.perform(put("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(25.0));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(productService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/v1/products/1"))
                .andExpect(status().isNoContent());
        
        verify(productService, times(1)).delete(1L);
    }
    
    private ProductDTO createProductDTO(Long id, String name, double price) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setPrice(price);
        productDTO.setCategoryId(1L);
        productDTO.setStorages(Collections.emptyList());
        return productDTO;
    }
}