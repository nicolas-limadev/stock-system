package com.bytedev.storage.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bytedev.storage.domain.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    
    private Long id;
    private String name;
    private double price;
    private Long categoryId;
    private List<ProductStorageDTO> storages = new ArrayList<>();

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryId = product.getCategory() != null ? 
            product.getCategory().getId() : null;
        
        if (product.getProductStorages() != null) {
            this.storages = product.getProductStorages().stream()
                .map(ProductStorageDTO::new)
                .collect(Collectors.toList());
        }
    }
}


