package com.example.microservice.mapper;

import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ShopsEntity;
import org.springframework.stereotype.Component;


@Component
public class MappingUtils {

    public ShopsEntity getMappedShopsEntity(Integer id_shop, String name_shop, String region_code){

        ShopsEntity se = new ShopsEntity();
        se.setId_shop(id_shop);
        se.setName_shop(name_shop);
        se.setRegion_code(region_code);

        return se;
    }

    public CountriesEntity getMappedCountriesEntity(Integer id_country, String name_country, String acronym_shop){

        CountriesEntity ce = new CountriesEntity();
        ce.setId_country(id_country);
        ce.setName_country(name_country);
        ce.setAcronym_shop(acronym_shop);

        return ce;
    }

}
