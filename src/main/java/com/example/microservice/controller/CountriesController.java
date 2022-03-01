package com.example.microservice.controller;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.service.CountryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/countries")
@CrossOrigin("http://localhost:4200")
public class CountriesController {

    private CountryServiceImpl countryServiceImpl;

    @Autowired
    public CountriesController(CountryServiceImpl countryServiceImpl){
        this.countryServiceImpl = countryServiceImpl;
    }





    @GetMapping(value = "/allCountries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CountriesDTO> allCountries(){

        return countryServiceImpl.getAllCountriesWithoutJoin();
    }

    @PostMapping(value = "/newCountrySP", produces = MediaType.APPLICATION_JSON_VALUE)
    public String insertNewCountry(@RequestParam("name_country")String name_country,
                                   @RequestParam("acronym_shop")String acronym_shop){

        return countryServiceImpl.insertNewCountrySP(name_country, acronym_shop);
    }

    @PutMapping(value = "/updateCountrySP", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCountryById(@RequestBody CountriesDTO countriesDTO){

        return countryServiceImpl.updateCountrySP(countriesDTO.getId_country(),countriesDTO.getName_country(),countriesDTO.getAcronym_shop());
    }

    @DeleteMapping(value = "/deleteCountrySP")
    public String deleteCountryById(@RequestParam("id_country")Integer id_country){

        return countryServiceImpl.deleteAndOrderCountryByIdSP(id_country);
    }



}
