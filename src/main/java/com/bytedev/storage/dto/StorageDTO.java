package com.bytedev.storage.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.bytedev.storage.domain.Storage;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Objeto de transferência de dados que representa um armazenamento")
public class StorageDTO {
    
    @Schema(description = "Identificador único do armazenamento", example = "1")
    private Long id;
    
    @Schema(description = "Nome do armazenamento", example = "Depósito Principal")
    private String name;
    
    @Schema(description = "Lista de produtos armazenados neste local")
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
