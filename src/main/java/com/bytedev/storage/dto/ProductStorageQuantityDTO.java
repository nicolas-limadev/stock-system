package com.bytedev.storage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
    @Schema(description = "ID do produto", example = "1")
    private Long productId;
    @Schema(description = "ID do estoque", example = "1")
    private Long storageId;
    @Schema(description = "Quantidade", example = "20")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantity;
}
