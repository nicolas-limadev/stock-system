package com.bytedev.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bytedev.stock.domain.Category;
import com.bytedev.stock.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    

    private CategoryService categoryService;


    @GetMapping
    public List<Category> listCategory(){
        
        return categoryService.listCategory();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(Long id){
        
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Category saveCategory(@Validated @RequestBody Category category){

        return categoryService.saveCategory(category);
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
