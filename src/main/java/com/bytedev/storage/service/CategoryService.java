package com.bytedev.storage.service;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.dto.CategoryDTO;
import com.bytedev.storage.exception.CustomException;
import com.bytedev.storage.exception.CustomNotFoundException;
import com.bytedev.storage.repository.CategoryRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        try {
            List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
            return categories.stream()
                    .map(CategoryDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException("Error fetching categories: " + e.getMessage());
        }
    }

    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryDTO(category);
    }

    public CategoryDTO create(CategoryDTO dto) {
        Category category = dto.toEntity();
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CustomNotFoundException("Category not found with id: " + id);
        }
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Error deleting category: " + e.getMessage());
        }
    }
}
