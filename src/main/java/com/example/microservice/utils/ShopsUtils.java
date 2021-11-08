package com.example.microservice.utils;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ShopsEntity;
import com.example.microservice.repository.ShopsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ShopsUtils {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ShopsUtils(EntityManager entityManager){
        this.entityManager = entityManager;
    }





//-----------------------------------------------STORED-PROCEDURE-----------------------------------------------



    public void sp_insertShopsCheckId(String name_shop, String region_code){

        StoredProcedureQuery spQueryInsertShopCheckId= entityManager.createStoredProcedureQuery("sp_insertShopsCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .setParameter(1, name_shop)
                .setParameter(2, region_code);

        spQueryInsertShopCheckId.execute();
    }



//-----------------------------------------------BUSINESS-LOGIC------------------------------------------------



    public ShopsEntity getLastShop(List<ShopsEntity> allShops) {

        ShopsEntity seResult = new ShopsEntity();
        for (int i = 0; i < allShops.size(); i++) {
            if (i == allShops.size() - 1) {
                seResult = allShops.get(i);
            }
        }

        return seResult;
    }

    public List<ShopsDTO> orderListShopsByIdSTREAM(List<ShopsDTO> list){

        List<ShopsDTO> sortedShops = list.stream()
                .sorted(Comparator.comparing(ShopsDTO::getId_shop)/*.reversed()*/)
                .collect(Collectors.toList());

        return sortedShops;
    }

    public List<ShopsEntity> getAllShopsEntityListUpdated(){

        List<ShopsEntity> seList = new ArrayList<>();
        try {
            Query q = entityManager.createQuery("SELECT * FROM shops");
            seList = q.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return seList;
    }

    public void checkRegionCode(String region_code, ShopsEntity se){

        CountriesEntity ce = new CountriesEntity();
        if(checkRegionCodeList(region_code)){
            if(region_code.equalsIgnoreCase("lo")){
                ce.setName_country("Lobardia");
                ce.setAcronym_shop("GDO");
                se.getCountryShop().add(ce);
            }
        }
    }

    public boolean checkRegionCodeList(String region_code){

        List<String> regionCodeList = new ArrayList<>();
        boolean exit = false;
        regionCodeList.add("AG");
        regionCodeList.add("AP");
        regionCodeList.add("BS");
        regionCodeList.add("FE");
        regionCodeList.add("LC");
        regionCodeList.add("LO");

        for(int i=0; i<regionCodeList.size(); i++){
            if(regionCodeList.get(i).equalsIgnoreCase(region_code)){
                exit = true;
            }
        }

        return exit;
    }











}







