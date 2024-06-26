package com.bytedev.storage.dto;

import com.bytedev.storage.domain.Product;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    
    private String name;
    private double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String category;

    public ProductDTO() {
    }
    
    public ProductDTO(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = product.getCategory().getName();
    }
}


