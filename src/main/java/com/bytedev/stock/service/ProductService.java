package com.bytedev.stock.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.bytedev.stock.domain.Product;
import com.bytedev.stock.repository.ProductRepository;

public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
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
