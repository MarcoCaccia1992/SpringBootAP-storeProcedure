package com.example.microservice.service;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ProductsEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountryService {

    public String insertNewCountrySP(String Country, String acronym_shop);

    public List<CountriesEntity> allCountries();

    public List<CountriesDTO> getAllCountriesWithoutJoin();

    String updateCountrySP(Integer id_country, String name_country, String acronym_shop);

    String deleteAndOrderCountryByIdSP(Integer id_country);
}
