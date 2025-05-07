package com.bytedev.storage.service;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.domain.Product;
import com.bytedev.storage.dto.ProductDTO;
import com.bytedev.storage.exception.CustomException;
import com.bytedev.storage.exception.CustomNotFoundException;
import com.bytedev.storage.repository.CategoryRepository;
import com.bytedev.storage.repository.ProductRepository;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;
    private Category category;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        // Setup test data
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic devices");
        category.setProducts(new ArrayList<>());

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(1500.0);
        product1.setCategory(category);
        product1.setProductStorages(new ArrayList<>());

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Smartphone");
        product2.setPrice(800.0);
        product2.setCategory(category);
        product2.setProductStorages(new ArrayList<>());

        productDTO = new ProductDTO();
        productDTO.setName("New Product");
        productDTO.setPrice(500.0);
        productDTO.setCategoryId(1L);
    }

    @Test
    void findAll_ShouldReturnAllProducts() {
        // Arrange
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductDTO> result = productService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Smartphone", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findAll_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(productRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> productService.findAll());
        assertTrue(exception.getMessage().contains("Error fetching products"));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        // Act
        ProductDTO result = productService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(1500.0, result.getPrice());
        assertEquals(1L, result.getCategoryId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.findById(99L));
        assertEquals("Product not found: 99", exception.getMessage());
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void create_WithValidCategory_ShouldReturnCreatedProduct() {
        // Arrange
        Product newProduct = productDTO.toEntity();
        newProduct.setCategory(category);
        
        Product savedProduct = new Product();
        savedProduct.setId(3L);
        savedProduct.setName(newProduct.getName());
        savedProduct.setPrice(newProduct.getPrice());
        savedProduct.setCategory(category);
        savedProduct.setProductStorages(new ArrayList<>());
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductDTO result = productService.create(productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New Product", result.getName());
        assertEquals(500.0, result.getPrice());
        assertEquals(1L, result.getCategoryId());
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void create_WithInvalidCategory_ShouldThrowException() {
        // Arrange
        productDTO.setCategoryId(99L);
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.create(productDTO));
        assertEquals("Category not found: 99", exception.getMessage());
        verify(categoryRepository, times(1)).findById(99L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void create_WithoutCategory_ShouldReturnCreatedProduct() {
        // Arrange
        productDTO.setCategoryId(null);
        
        Product newProduct = productDTO.toEntity();
        
        Product savedProduct = new Product();
        savedProduct.setId(3L);
        savedProduct.setName(newProduct.getName());
        savedProduct.setPrice(newProduct.getPrice());
        savedProduct.setProductStorages(new ArrayList<>());
        
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductDTO result = productService.create(productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New Product", result.getName());
        assertEquals(500.0, result.getPrice());
        assertNull(result.getCategoryId());
        verify(categoryRepository, never()).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void update_WhenProductAndCategoryExist_ShouldReturnUpdatedProduct() {
        // Arrange
        ProductDTO updateDTO = new ProductDTO();
        updateDTO.setName("Updated Product");
        updateDTO.setPrice(600.0);
        updateDTO.setCategoryId(1L);
        
        Product existingProduct = product1;
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(600.0);
        updatedProduct.setCategory(category);
        updatedProduct.setProductStorages(new ArrayList<>());
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        ProductDTO result = productService.update(1L, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Product", result.getName());
        assertEquals(600.0, result.getPrice());
        assertEquals(1L, result.getCategoryId());
        verify(productRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void update_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.update(99L, productDTO));
        assertEquals("Product not found: 99", exception.getMessage());
        verify(productRepository, times(1)).findById(99L);
        verify(categoryRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void update_WhenCategoryDoesNotExist_ShouldThrowException() {
        // Arrange
        productDTO.setCategoryId(99L);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.update(1L, productDTO));
        assertEquals("Category not found: 99", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(99L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void delete_WhenProductExists_ShouldDeleteProduct() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        // Act
        productService.delete(1L);

        // Assert
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenProductDoesNotExist_ShouldThrowCustomNotFoundException() {
        // Arrange
        when(productRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> productService.delete(99L));
        assertEquals("Product not found with id: 99", exception.getMessage());
        verify(productRepository, times(1)).existsById(99L);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(productRepository).deleteById(1L);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> productService.delete(1L));
        assertTrue(exception.getMessage().contains("Error deleting product"));
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}