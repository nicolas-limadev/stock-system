package com.bytedev.storage.dto;

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
@Schema(description = "DTO para operações de quantidade de produto no estoque")
public class ProductStorageQuantityDTO {
    @NotNull(message = "O ID do produto é obrigatório")
    @Schema(description = "ID do produto", example = "1")
    private Long productId;
    
    @NotNull(message = "O ID do estoque é obrigatório")
    @Schema(description = "ID do estoque", example = "1")
    private Long storageId;
    
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Schema(description = "Quantidade", example = "20")
    private Integer quantity;
}
