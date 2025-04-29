package com.bytedev.storage.dto;

import com.bytedev.storage.domain.ProductStorage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStorageDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Long storageId;
    private String storageName;
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
