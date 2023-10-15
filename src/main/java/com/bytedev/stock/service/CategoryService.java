package com.bytedev.stock.service;

import com.bytedev.stock.domain.Category;
import com.bytedev.stock.dto.CategoryDTO;
import com.bytedev.stock.dto.ProductDTO;
import com.bytedev.stock.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public List<CategoryDTO> listCategoryWithProducts() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoryDTO> categoryDTOs = new ArrayList<>();

    for (Category category : categories) {
      CategoryDTO DTO = new CategoryDTO();
      DTO.setId(category.getId());
      DTO.setName(category.getName());
      DTO.setDescription(category.getDescription());

      List<ProductDTO> productDTOs = category
        .getProducts()
        .stream()
        .map(product -> {
          ProductDTO productDTO = new ProductDTO();
          productDTO.setId(product.getId());
          productDTO.setName(product.getName());
          productDTO.setPrice(product.getPrice());
          return productDTO;
        })
        .collect(Collectors.toList());

      DTO.setProducts(productDTOs);
      categoryDTOs.add(DTO);
    }
    return categoryDTOs;
  }

  public Category getCategoryById(Long id) {
    return categoryRepository.findById(id).orElse(null);
  }

  public Category saveCategory(CategoryDTO categoryDTO) {

    if (categoryDTO.getName() == null || categoryDTO.getDescription() == null) {
        throw new RuntimeException("All fields must be filled");
    }

    Category category = new Category();
    category.setName(categoryDTO.getName());
    category.setDescription(categoryDTO.getDescription());

    return categoryRepository.save(category);
  }

  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  public Category updateCategory(Long id, Category category) {
    Category existingCategory = categoryRepository.findById(id).orElse(null);
    existingCategory.setName(category.getName());
    existingCategory.setDescription(category.getDescription());
    return categoryRepository.save(existingCategory);
  }
}
