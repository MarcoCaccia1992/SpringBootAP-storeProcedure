package com.example.microservice.controller;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.service.CountryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/countries")
public class CountriesController {

    private CountryServiceImpl countryServiceImpl;

    @Autowired
    public CountriesController(CountryServiceImpl countryServiceImpl){
        this.countryServiceImpl = countryServiceImpl;
    }





    @GetMapping(value = "/allCountry", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CountriesDTO> allCountries(){

        return countryServiceImpl.getAllCountriesWithoutJoin();
    }

    @PostMapping(value = "/newCountry", produces = MediaType.APPLICATION_JSON_VALUE)
    public String insertNewCountry(@RequestParam("name_country")String name_country,
                                   @RequestParam("acronym_shop")String acronym_shop){

        String result = countryServiceImpl.insertNewCountry(name_country, acronym_shop);
        return result;
    }

}
