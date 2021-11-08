package com.example.microservice.service;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ShopsEntity;

import java.util.List;

public interface ShopsService {

    public String insertNewShop(String name_shop, String region_code);

    public List<ShopsDTO> getAllShopsWithoutJoin();

}
