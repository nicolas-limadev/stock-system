package com.bytedev.storage.service;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.dto.CategoryDTO;
import com.bytedev.storage.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Injeção de dependência via construtor
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Método para encontrar uma categoria por ID
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryDTO(category);
    }

    // Método para salvar uma nova categoria
    public CategoryDTO create(CategoryDTO dto) {
        Category category = dto.toEntity();
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    // Método para atualizar uma categoria existente
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    // Método para deletar uma categoria
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}





