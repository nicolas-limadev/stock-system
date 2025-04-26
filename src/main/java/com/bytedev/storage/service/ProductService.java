package com.bytedev.storage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytedev.storage.domain.Category;
import com.bytedev.storage.domain.Product;
import com.bytedev.storage.domain.ProductStorage;
import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.ProductDTO;
import com.bytedev.storage.dto.ProductStorageDTO;
import com.bytedev.storage.repository.CategoryRepository;
import com.bytedev.storage.repository.ProductRepository;
import com.bytedev.storage.repository.ProductStorageRepository;
import com.bytedev.storage.repository.StorageRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> listProductWithCategoryStorage() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::new)
                .orElse(null);
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + productDTO.getCategoryId()));
            product.setCategory(category);
        }

        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDTO.getName());
                    product.setPrice(productDTO.getPrice());
                    
                    if (productDTO.getCategoryId() != null) {
                        Category category = categoryRepository.findById(productDTO.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found: " + productDTO.getCategoryId()));
                        product.setCategory(category);
                    }
                    
                    return new ProductDTO(productRepository.save(product));
                })
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }
}
