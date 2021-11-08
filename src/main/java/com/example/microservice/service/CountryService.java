package com.example.microservice.service;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ProductsEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

    public String insertNewCountry(String Country);

    public List<CountriesEntity> allCountries();

    public List<CountriesDTO> getAllCountriesWithoutJoin();

}
