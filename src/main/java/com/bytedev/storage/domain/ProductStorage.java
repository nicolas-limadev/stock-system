package com.bytedev.storage.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id") // A coluna product_id deve existir na tabela
    private Product product;

    @ManyToOne
    @JoinColumn(name = "storage_id") // A coluna storage_id deve existir na tabela
    private Storage storage;

    private Integer quantity;
}
