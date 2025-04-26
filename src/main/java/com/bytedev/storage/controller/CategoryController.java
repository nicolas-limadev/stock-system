package com.bytedev.storage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.dto.CategoryDTO;
import com.bytedev.storage.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> listCategory(){
        
        List<CategoryDTO> categories = categoryService.listCategoryWithProducts();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(Long id){
        
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public Category updateCategory(Long id, Category category) {
        
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(Long id){
        
        categoryService.deleteCategory(id);
    }

    

}
