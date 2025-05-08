package com.bytedev.storage.dto;

import com.bytedev.storage.domain.Category;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Objeto de transferência de dados que representa uma categoria de produtos")
public class CategoryDTO {

    @Schema(description = "Identificador único da categoria", example = "1")
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Schema(description = "Nome da categoria", example = "Eletrônicos")
    private String name;

    @Schema(description = "Descrição detalhada da categoria", example = "Produtos eletrônicos e acessórios")
    private String description;
    
    @Schema(description = "Lista de produtos pertencentes a esta categoria")
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
        category.setName(this.name);
        category.setDescription(this.description);
        return category;
    }
}






