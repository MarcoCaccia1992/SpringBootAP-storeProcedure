package com.example.microservice.service;

import com.example.microservice.DTO.InnerJoinShopsProductsClassDTO;
import com.example.microservice.DTO.InnerJoinShopsProductsInterfaceDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ShopsEntity;
import com.example.microservice.repository.ShopsRepository;
import com.example.microservice.utils.ShopsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsServiceImpl implements ShopsService{

    private ShopsUtils shopsUtils;
    private ShopsRepository shopsRepository;

    @Autowired
    public ShopsServiceImpl(ShopsUtils shopsUtils, ShopsRepository shopsRepository){
        this.shopsUtils = shopsUtils;
        this.shopsRepository = shopsRepository;
    }





    public List<InnerJoinShopsProductsInterfaceDTO> getJoinShopsProductsNATIVEQUERY(){

        List<InnerJoinShopsProductsInterfaceDTO> result = shopsRepository.resultInnerJoinNATIVE();
        return result;
    }

    public List<InnerJoinShopsProductsClassDTO> getJoinShopsProductsJPQL(){

        List<InnerJoinShopsProductsClassDTO> result = shopsRepository.resultInnerJoinJPQL();
        return result;
    }

    @Override
    public String insertNewShop(String name_shop, String region_code) {

        if(region_code == null) {
            region_code = "NOT ITALY REGION";
            shopsUtils.sp_insertShopsCheckId(name_shop, region_code);
        }
        if(region_code != null){
            ShopsEntity seToInsertIntoDB = shopsUtils.checkRegionCodeAndJoinShop(name_shop, region_code);
            shopsRepository.save(seToInsertIntoDB);
        }
        List<ShopsEntity> allShopsUpdated = shopsRepository.findAll();
        ShopsEntity lastShopSaved = shopsUtils.getLastShop(allShopsUpdated);

        return "You've already insert:\n" + "ID_SHOP: " + lastShopSaved.getId_shop() + "\n" + "NAME_SHOP: " + lastShopSaved.getName_shop();
    }

    @Override
    public List<ShopsDTO> getAllShopsWithoutJoin() {

        List<ShopsDTO> result = new ArrayList<>();
        result = shopsRepository.getAllShopsWithoutJoin();
        result = shopsUtils.orderListShopsByIdSTREAM(result);
        return result;
    }
}
