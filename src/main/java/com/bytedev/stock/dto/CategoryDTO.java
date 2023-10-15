package com.bytedev.stock.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bytedev.stock.domain.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    
    private Long id;
    private String name;
    private String description;


    private List<ProductDTO> products;

    public CategoryDTO() {
    }

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.products = category.getProducts().stream()
        .map(ProductDTO::new)
        .collect(Collectors.toList());
    }
}
