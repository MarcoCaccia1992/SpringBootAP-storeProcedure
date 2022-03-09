package com.example.microservice.utils;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.entity.ShopsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
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
    public void sp_updateCountryById(Integer id_country, String name_country, String acronym_shop){

        StoredProcedureQuery spQueryUpdateCountryById= entityManager.createStoredProcedureQuery("sp_updateCountries")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .setParameter(1, id_country)
                .setParameter(2, name_country)
                .setParameter(3, acronym_shop);

        spQueryUpdateCountryById.execute();
    }

    public void sp_deleteCountryById(Integer id_country){

        StoredProcedureQuery spQueryDeleteCountryById= entityManager.createStoredProcedureQuery("sp_deleteCountries")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, id_country);

        spQueryDeleteCountryById.execute();
    }

    public void sp_orderCountryById(Integer id_country){

        StoredProcedureQuery spQueryOrderCountryById= entityManager.createStoredProcedureQuery("sp_orderCountriesIDS")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, id_country);

        spQueryOrderCountryById.execute();
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

    public CountriesEntity getCountryByName(List<CountriesEntity> allCountries, String name_country){

        CountriesEntity ceResult = new CountriesEntity();
        for(CountriesEntity c : allCountries){
            if (c.getName_country().equalsIgnoreCase(name_country)) {
                ceResult = c;
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


