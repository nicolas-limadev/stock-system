package com.bytedev.storage.dto;

import com.bytedev.storage.domain.Product;
import com.bytedev.storage.domain.Storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageDTO {
    
    private Long id;
    private int quantity;
    private Product product;

    public StorageDTO(){
        
    }

    public StorageDTO(Storage storage) {

        this.id = storage.getId();
        this.quantity = storage.getQuantity();
        this.product = storage.getProduct();
        
    }

}
