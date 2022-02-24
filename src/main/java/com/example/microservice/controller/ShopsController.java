package com.example.microservice.controller;

import com.example.microservice.DTO.InnerJoinShopsProductsClassDTO;
import com.example.microservice.DTO.InnerJoinShopsProductsInterfaceDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.service.ShopsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
@CrossOrigin("http://localhost:4200")
public class ShopsController {

    private ShopsServiceImpl shopsServiceImpl;

    @Autowired
    public ShopsController(ShopsServiceImpl shopsServiceImpl){
        this.shopsServiceImpl = shopsServiceImpl;
    }






    @GetMapping(value = "/allShops", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/newShopSP", produces = MediaType.APPLICATION_JSON_VALUE)
    public String insertNewShop(@RequestParam("name_shop")String name_shop,
                                @RequestParam("region_code") String region_code){

         String result = shopsServiceImpl.insertNewShopSP(name_shop, region_code);
         return result;
    }

    @PutMapping(value = "/updateShopSP", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateShopSP(@RequestParam("id_shop")Integer id_shop,
                             @RequestParam("name_shop")String name_shop,
                             @RequestParam("region_code")String region_code){

        String result = shopsServiceImpl.updateShopBySP(id_shop, name_shop, region_code);
        return result;
    }

    @PutMapping(value = "/updateShopQUERY", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateShopQUERY(@RequestParam("id_shop")Integer id_shop,
                             @RequestParam("name_shop")String name_shop,
                             @RequestParam("region_code")String region_code){

        shopsServiceImpl.updateShopByQUERY(id_shop, name_shop, region_code);
        return "you've alredy updeated your shop: \n" + id_shop + "\n" + name_shop + "\n" + region_code;
    }

    @DeleteMapping(value = "/deleteShopByIdSP", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteShopByIdSP(@RequestParam("id_shop")Integer id_shop){

        return shopsServiceImpl.deleteShopByIdSP(id_shop);
    }
}
