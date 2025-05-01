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
@Schema(description = "DTO para atualizar a quantidade do produto no estoque")
public class UpdateProductQuantityDTO {
    @Schema(description = "ID do produto", example = "1")
    private Long productId;
    @Schema(description = "ID do estoque", example = "1")
    private Long storageId;
    @Schema(description = "Nova quantidade", example = "10")
    private Integer newQuantity;
}

