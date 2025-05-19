package com.bytedev.storage.service;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.dto.CategoryDTO;
import com.bytedev.storage.exception.CustomException;
import com.bytedev.storage.exception.CustomNotFoundException;
import com.bytedev.storage.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category1;
    private Category category2;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        // Setup test data
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");
        category1.setDescription("Electronic devices");
        category1.setProducts(new ArrayList<>());

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Books");
        category2.setDescription("Reading materials");
        category2.setProducts(new ArrayList<>());

        categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Category");
        categoryDTO.setDescription("New Description");
    }

    @Test
    void findAll_ShouldReturnAllCategories() {
        // Arrange
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll(any(Sort.class))).thenReturn(categories);

        // Act
        List<CategoryDTO> result = categoryService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Electronics", result.get(0).getName());
        assertEquals("Books", result.get(1).getName());
        verify(categoryRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void findAll_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(categoryRepository.findAll(any(Sort.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> categoryService.findAll());
        assertTrue(exception.getMessage().contains("Error fetching categories"));
        verify(categoryRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void findById_WhenCategoryExists_ShouldReturnCategory() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

        // Act
        CategoryDTO result = categoryService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenCategoryDoesNotExist_ShouldThrowException() {
        // Arrange
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.findById(99L));
        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(99L);
    }

    @Test
    void create_ShouldReturnCreatedCategory() {
        // Arrange
        Category newCategory = categoryDTO.toEntity();
        Category savedCategory = new Category();
        savedCategory.setId(3L);
        savedCategory.setName(newCategory.getName());
        savedCategory.setDescription(newCategory.getDescription());
        
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CategoryDTO result = categoryService.create(categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New Category", result.getName());
        assertEquals("New Description", result.getDescription());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void update_WhenCategoryExists_ShouldReturnUpdatedCategory() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        
        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName(categoryDTO.getName());
        updatedCategory.setDescription(categoryDTO.getDescription());
        
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // Act
        CategoryDTO result = categoryService.update(1L, categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Category", result.getName());
        assertEquals("New Description", result.getDescription());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void update_WhenCategoryDoesNotExist_ShouldThrowException() {
        // Arrange
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.update(99L, categoryDTO));
        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(99L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void delete_WhenCategoryExists_ShouldDeleteCategory() {
        // Arrange
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        // Act
        categoryService.delete(1L);

        // Assert
        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_WhenCategoryDoesNotExist_ShouldThrowCustomNotFoundException() {
        // Arrange
        when(categoryRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> categoryService.delete(99L));
        assertEquals("Category not found with id: 99", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(99L);
        verify(categoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_WhenExceptionOccurs_ShouldThrowCustomException() {
        // Arrange
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(categoryRepository).deleteById(1L);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> categoryService.delete(1L));
        assertTrue(exception.getMessage().contains("Error deleting category"));
        verify(categoryRepository, times(1)).existsById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}