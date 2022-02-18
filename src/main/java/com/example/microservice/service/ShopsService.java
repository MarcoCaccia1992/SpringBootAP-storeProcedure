package com.example.microservice.service;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ShopsEntity;

import java.util.List;

public interface ShopsService {

    public String insertNewShop(String name_shop, String region_code);

    public List<ShopsDTO> getAllShopsWithoutJoin();

    public String updateShopBySP(Integer id_shop, String name_shop, String region_code);

    public String updateShopByQUERY(Integer id_shop, String name_shop, String region_code);

    public String deleteShopByIdSP(Integer id_shop);

}
