package com.bytedev.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    
    private Long id;
    private String name;
    private double price;
    private String category;

    public ProductDTO() {
    }
    
    public ProductDTO(Long id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}


