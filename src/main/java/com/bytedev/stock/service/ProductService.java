package com.bytedev.stock.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytedev.stock.domain.Category;
import com.bytedev.stock.domain.Product;
import com.bytedev.stock.dto.ProductDTO;
import com.bytedev.stock.repository.CategoryRepository;
import com.bytedev.stock.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductDTO> listProductWithCategory(){

        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for(Product product : products){
            ProductDTO DTO = new ProductDTO();
            DTO.setId(product.getId());
            DTO.setName(product.getName());
            DTO.setPrice(product.getPrice());

            productDTOs.add(DTO);
        }
        return productDTOs;
    }

    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            return new ProductDTO(product);
        }
        return null;
    }

    public ProductDTO saveProduct(Product productRequest){
        
        String categoryName = productRequest.getCategory().getName();
        Category category = categoryRepository.findByName(categoryName);

        if(category == null){
            throw new RuntimeException("Category name is required");
        }

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return new ProductDTO(savedProduct);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);
    }
}
