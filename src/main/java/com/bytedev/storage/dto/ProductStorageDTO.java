package com.bytedev.storage.dto;

import com.bytedev.storage.domain.ProductStorage;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferência de dados que representa um produto em um armazenamento")
public class ProductStorageDTO {
    @Schema(description = "Identificador único da relação produto-armazenamento", example = "1")
    private Long id;
    
    @NotNull(message = "O id do produto é obrigatório")
    @Schema(description = "Identificador único do produto", example = "42")
    private Long productId;
    
    @Schema(description = "Nome do produto para exibição", example = "Smartphone XYZ")
    private String productName;
    
    @NotNull(message = "O id do armazenamento é obrigatório")
    @Schema(description = "Identificador único do armazenamento", example = "5")
    private Long storageId;
    
    @Schema(description = "Nome do armazenamento para exibição", example = "Depósito Central")
    private String storageName;
    
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Schema(description = "Quantidade do produto disponível no armazenamento", example = "100")
    private Integer quantity;
    
    public ProductStorageDTO(ProductStorage productStorage) {
        if (productStorage != null) {
            this.id = productStorage.getId();
            if (productStorage.getProduct() != null) {
                this.productId = productStorage.getProduct().getId();
                this.productName = productStorage.getProduct().getName();
            }
            if (productStorage.getStorage() != null) {
                this.storageId = productStorage.getStorage().getId();
                this.storageName = productStorage.getStorage().getName();
            }
            this.quantity = productStorage.getQuantity();
        }
    }
}
