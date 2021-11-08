package com.example.microservice.utils;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.entity.ShopsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountriesUtils {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CountriesUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }





//-----------------------------------------------STORED-PROCEDURE-----------------------------------------------


    public void sp_insertCountriesCheckId(String name_country, String acronym_shop){

        StoredProcedureQuery spQueryInsertCountryCheckId= entityManager.createStoredProcedureQuery("sp_insertCountriesCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .setParameter(1, name_country)
                .setParameter(2, acronym_shop);

        spQueryInsertCountryCheckId.execute();
    }


//------------------------------------------------BUSINESS-LOGIC------------------------------------------------


    public CountriesEntity getLastCountry(List<CountriesEntity> allCountries){

        CountriesEntity ceResult = new CountriesEntity();
        for(int i=0; i<allCountries.size(); i++){
            if(i == allCountries.size() -1){
                ceResult = allCountries.get(i);
            }
        }

        return ceResult;
    }

    // metodo per tornare una lista ordinata tramite lambda e con il reversed() le darà al contrario
    public List<CountriesEntity> orderListCountriesByIdSTREAM(List<CountriesEntity> list){

        List<CountriesEntity> sortedCountries = list.stream()
                .sorted(Comparator.comparing(CountriesEntity::getId_country)/*.reversed()*/)
                .collect(Collectors.toList());

        return sortedCountries;
    }

    public List<CountriesDTO> orderListCountriesDTOByIdSTREAM(List<CountriesDTO> list){

        List<CountriesDTO> sortedCountries = list.stream()
                .sorted(Comparator.comparing(CountriesDTO::getId_country)/*.reversed()*/)
                .collect(Collectors.toList());

        return sortedCountries;
    }






}





    /*List<ShopsDTO> allShops = getAllShops();
    ShopsUtils su = new ShopsUtils();
    List<ShopsEntity> allShopsEntity = new ArrayList<>();
        for(int i=0; i<allShops.size(); i++){
        ShopsEntity se = new ShopsEntity();
        se.setId_shop(allShops.get(i).getId_shop());
        se.setName_shop(allShops.get(i).getName_shop());
        allShopsEntity.add(se);
    }
    ShopsEntity lastShop = su.getLastShop(allShopsEntity);
    CountriesEntity ce = new CountriesEntity();
        ce.getCountryShop().add(lastShop);
        countriesRepository.save(ce);*/

