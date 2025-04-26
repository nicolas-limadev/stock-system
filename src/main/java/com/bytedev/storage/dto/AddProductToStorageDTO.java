package com.bytedev.storage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para adicionar produto ao estoque")
public class AddProductToStorageDTO {
    @Schema(description = "ID do produto", example = "1")
    private Long productId;
    @Schema(description = "ID do estoque", example = "1")
    private Long storageId;
    @Schema(description = "Quantidade", example = "20")
    private Integer quantity;
}