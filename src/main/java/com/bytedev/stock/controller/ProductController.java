package com.bytedev.stock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytedev.stock.domain.Product;
import com.bytedev.stock.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("products")
@RestController
public class ProductController {
    
    private ProductService productService;

    @GetMapping
    public List<Product> listProduct(){
        return productService.listProduct();
    }

    @GetMapping("/{id}")
    public Product getProductById(Long id){
        return productService.getProductById(id);
    }


    @PostMapping("/create")
    public Product saveProduct(Product product){
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(Long id, Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(Long id){
        productService.deleteProduct(id);
    }
}
