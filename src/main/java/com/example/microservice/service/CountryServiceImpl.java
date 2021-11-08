package com.example.microservice.service;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.repository.CountriesRepository;
import com.example.microservice.utils.CountriesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryServiceImpl implements CountryService{

    private CountriesRepository countriesRepository;
    private CountriesUtils countriesUtils;

    @Autowired
    public CountryServiceImpl(CountriesRepository countriesRepository, CountriesUtils countriesUtils){
        this.countriesRepository = countriesRepository;
        this.countriesUtils = countriesUtils;
    }






    @Override
    public String insertNewCountry(String name_country, String acronym_shop) {

        countriesUtils.sp_insertCountriesCheckId(name_country, acronym_shop);

        List<CountriesEntity> allCountries = allCountries();
        CountriesEntity ce = countriesUtils.getLastCountry(allCountries);


        return "You've already saved this:\n" + "COUNTRY_ID: "
                + ce.getId_country() + "\n" + "COUNTRY_NAME: "
                + ce.getName_country();
    }

    @Override
    public List<CountriesEntity> allCountries() {

        List<CountriesEntity> result = new ArrayList<>();
        result = countriesRepository.findAll();
        result = countriesUtils.orderListCountriesByIdSTREAM(result);
        return result;

    }
    @Override
    public List<CountriesDTO> getAllCountriesWithoutJoin(){

        List<CountriesDTO> result = new ArrayList<>();
        result = countriesRepository.getAllCountriesWithoutJoin();
        result = countriesUtils.orderListCountriesDTOByIdSTREAM(result);
        return result;
    }

}
