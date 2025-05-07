package com.bytedev.storage.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bytedev.storage.domain.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Objeto de transferência de dados que representa um produto")
public class ProductDTO {

    @Schema(description = "Identificador único do produto", example = "1")
    private Long id;
    
    @NotBlank(message = "O nome do produto é obrigatório")
    @Schema(description = "Nome do produto", example = "Smartphone XYZ")
    private String name;
    
    @Schema(description = "Preço do produto", example = "1299.99")
    private double price;

    @NotNull(message = "O id da categoria é obrigatório")
    @Schema(description = "Identificador da categoria do produto", example = "5")
    private Long categoryId;
    
    @Schema(description = "Lista de storage onde o produto está disponível")
    private List<ProductStorageDTO> storages = new ArrayList<>();

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryId = product.getCategory() != null ? product.getCategory().getId() : null;

        if (product.getProductStorages() != null) {
            this.storages = product.getProductStorages().stream()
                    .map(ProductStorageDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public Product toEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setPrice(this.price);
        return product;
    }
}
