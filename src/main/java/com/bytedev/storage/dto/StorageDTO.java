package com.bytedev.storage.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bytedev.storage.domain.Storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StorageDTO {
    
    private Long id;
    private String name;
    private List<ProductStorageDTO> products;

    public StorageDTO(Storage storage) {
        if (storage != null) {
            this.id = storage.getId();
            this.name = storage.getName();
            if (storage.getProductStorages() != null) {
                this.products = storage.getProductStorages().stream()
                    .map(ProductStorageDTO::new)
                    .collect(Collectors.toList());
            }
        }
    }

    public Storage toEntity(){
        Storage storage = new Storage();
        storage.setName(this.name);
        return storage;
    }
}
