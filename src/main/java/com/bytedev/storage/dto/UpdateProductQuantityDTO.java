package com.bytedev.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductQuantityDTO {
    private Long productId;
    private Long storageId;
    private Integer newQuantity;
}

