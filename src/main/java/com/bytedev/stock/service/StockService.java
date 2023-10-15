package com.bytedev.stock.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bytedev.stock.repository.StockRepository;

public class StockService {
    
    @Autowired
    private StockRepository stockRepository;
}
