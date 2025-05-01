package com.bytedev.storage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.domain.Product;
import com.bytedev.storage.dto.ProductDTO;
import com.bytedev.storage.repository.CategoryRepository;
import com.bytedev.storage.repository.ProductRepository;



import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::new)
                .orElse(null);
    }

    public ProductDTO create(ProductDTO dto) {
        Product product = dto.toEntity();
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));
            product.setCategory(category);
        }
        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        return productRepository.findById(id)
        .map(product -> {
            dto.toEntity();
            if (dto.getCategoryId() != null) {
                Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));
                product.setCategory(category);
            }
            return new ProductDTO(productRepository.save(product));
        })
        .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
