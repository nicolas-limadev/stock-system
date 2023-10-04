package com.bytedev.stock.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bytedev.stock.repository.ProductRepository;

public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
}
