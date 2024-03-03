package com.bytedev.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.bytedev.storage.domain.Storage;
import com.bytedev.storage.dto.StorageDTO;
import com.bytedev.storage.repository.StorageRepository;

@Service
public class StorageService {
    
 @Autowired
 StorageRepository storageRepository;

 public List<StorageDTO> listStorage(){
    List<Storage> storages = storageRepository.findAll();
    List<StorageDTO> storageDTOs = new ArrayList<>();

    for (Storage storage : storages) {
      StorageDTO storageDTO = new StorageDTO();
      storageDTO.setId(storage.getId());
      storageDTO.setQuantity(storage.getQuantity());
      storageDTO.setProduct(storage.getProduct());
      storageDTOs.add(storageDTO);
    }

    return storageDTOs;
 }

 public StorageDTO saveStorage(Storage storageRequest){

   Storage storage = new Storage();
   storage.setQuantity(storageRequest.getQuantity());
   storage.setProduct(storageRequest.getProduct());

    Storage savedStorage = storageRepository.save(storage);

    return new StorageDTO(savedStorage);
 }
}
