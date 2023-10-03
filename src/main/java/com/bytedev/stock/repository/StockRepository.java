package com.bytedev.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytedev.stock.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{
    
}
