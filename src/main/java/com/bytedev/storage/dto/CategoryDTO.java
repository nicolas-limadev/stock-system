package com.bytedev.storage.dto;

import com.bytedev.storage.domain.Category;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório")
    private String name;

    private String description;
    private List<ProductDTO> products;

    public CategoryDTO() {
        this.products = new ArrayList<>();
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.products = category.getProducts() != null ?
                category.getProducts().stream()
                        .map(ProductDTO::new)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }

    public Category toEntity() {
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        category.setDescription(this.description);
        return category;
    }
}






