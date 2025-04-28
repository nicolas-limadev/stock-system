package com.bytedev.storage.controller;

import com.bytedev.storage.dto.CategoryDTO; // Corrigir importação
import com.bytedev.storage.service.CategoryService; // Corrigir importação
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;  // Alterando para o novo pacote
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Buscar Categoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        CategoryDTO dto = categoryService.getById(id);
        return ResponseEntity.ok(dto);
    }

    // Atualizar Categoria
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO dto
    ) {
        CategoryDTO updatedCategory = categoryService.update(id, dto);
        return ResponseEntity.ok(updatedCategory);
    }

    // Criar Categoria
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) {
        CategoryDTO createdCategory = categoryService.create(dto);
        return ResponseEntity.status(201).body(createdCategory);
    }

    // Deletar Categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



