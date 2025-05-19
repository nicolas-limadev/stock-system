package com.bytedev.storage.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    private List<ProductStorage> productStorages;

    public List<ProductStorage> getProductStorages() {
        return productStorages;
    }
}


