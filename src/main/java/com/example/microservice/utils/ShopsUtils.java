package com.example.microservice.utils;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ShopsEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
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



    public void sp_insertShopsCheckId(String name_shop){

        StoredProcedureQuery spQueryInsertShopCheckId= entityManager.createStoredProcedureQuery("sp_insertShopsCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .setParameter(1, name_shop);

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











}







