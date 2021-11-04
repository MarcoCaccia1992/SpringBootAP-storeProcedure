package com.example.microservice.controller;

import com.example.microservice.DTO.InnerJoinShopsProductsClassDTO;
import com.example.microservice.DTO.InnerJoinShopsProductsInterfaceDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ShopsEntity;
import com.example.microservice.service.ShopsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopsController {

    private ShopsServiceImpl shopsServiceImpl;

    @Autowired
    public ShopsController(ShopsServiceImpl shopsServiceImpl){
        this.shopsServiceImpl = shopsServiceImpl;

    }






    @GetMapping(value = "/getAllShops", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopsDTO> getAllShops(){

        return shopsServiceImpl.getAllShopsWithoutJoin();
    }

    @GetMapping(value = "/getInnerJoinShopsProductsNATIVEQUERY", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InnerJoinShopsProductsInterfaceDTO> getJoinNATIVEQUERY(){

        List<InnerJoinShopsProductsInterfaceDTO> res = shopsServiceImpl.getJoinShopsProductsNATIVEQUERY();
        return res;
    }

    @GetMapping(value = "/getInnerJoinShopsProductsJPQL", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InnerJoinShopsProductsClassDTO> getJoinJPQL(){

        List<InnerJoinShopsProductsClassDTO> res = shopsServiceImpl.getJoinShopsProductsJPQL();
        return res;
    }

    @PostMapping(value = "/insertNewShop", produces = MediaType.APPLICATION_JSON_VALUE)
    public String insertNewShop(@RequestParam("name_shop")String name_shop){

        return shopsServiceImpl.insertNewShop(name_shop);
    }
}
