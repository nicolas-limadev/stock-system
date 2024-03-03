package com.bytedev.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.service.StorageService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;


@RequestMapping("/storages")
@RestController
public class StorageController {
    
    @Autowired
    private StorageService storageService;

    @GetMapping
    public List<StorageDTO> listStorage(){
        return storageService.listStorage();

    }

    @PostMapping("/create")
    public StorageDTO createStorage(@RequestBody Storage storage){
        return storageService.saveStorage(storage);
    }
    
}
