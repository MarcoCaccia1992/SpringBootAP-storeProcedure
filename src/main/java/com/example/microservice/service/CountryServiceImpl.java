package com.example.microservice.service;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.exception.CustomException;
import com.example.microservice.repository.CountriesRepository;
import com.example.microservice.utils.CountriesUtils;
import com.example.microservice.utils.JoinUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CountryServiceImpl implements CountryService {

    private CountriesRepository countriesRepository;
    private CountriesUtils countriesUtils;
    private JoinUtils joinUtils;

    @Autowired
    public CountryServiceImpl(CountriesRepository countriesRepository, CountriesUtils countriesUtils, JoinUtils joinUtils) {
        this.countriesRepository = countriesRepository;
        this.countriesUtils = countriesUtils;
        this.joinUtils = joinUtils;
    }


    @Override
    public String insertNewCountrySP(String name_country, String acronym_shop) {

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
    public List<CountriesDTO> getAllCountriesWithoutJoin() {

        List<CountriesDTO> result = new ArrayList<>();
        result = countriesRepository.getAllCountriesWithoutJoin();
        result = countriesUtils.orderListCountriesDTOByIdSTREAM(result);
        return result;
    }

    @Override
    public String updateCountrySP(Integer id_country, String name_country, String acronym_shop) {

        Optional<CountriesEntity> countryBeforeUpdate = countriesRepository.findById(id_country);
        CountriesEntity countryBeforeUpdateOBJ = countryBeforeUpdate.get();
        countriesUtils.sp_updateCountryById(id_country, name_country, acronym_shop);
        return "you've already updated country with ID: " + id_country.toString() + "\n" +
                "FROM: \n" + "\n" + countryBeforeUpdateOBJ.getName_country() + "\n" +
                countryBeforeUpdateOBJ.getAcronym_shop() + "\n" + "\n" + "TO: " + "\n" + "\n" +
                name_country + "\n" + acronym_shop;
    }

    @Override
    public String deleteAndOrderCountryByIdSP(Integer id_country) {

        Optional<CountriesEntity> countriesDeleted = countriesRepository.findById(id_country);
        //joinUtils.queryToDeleteRecordMTMBYId("id_country", countriesDeleted.get().getId_country());
        countriesUtils.sp_deleteCountryById(id_country);
        countriesUtils.sp_orderCountryById(id_country);
        return "You've already deleted: \n" +
                countriesDeleted.get().getId_country() + "\n" +
                countriesDeleted.get().getName_country() + "\n" +
                countriesDeleted.get().getAcronym_shop();
    }

    @Override
    public CountriesDTO findCountryById(Integer id_country) throws CustomException{

        Optional<CountriesEntity> c = countriesRepository.findById(id_country);

        if(!c.isPresent()){
            throw new CustomException("Country not present ", " please check the 'id_country' inserted into REQUEST or not exist or wrong Id");
        }

        CountriesDTO c_dto = new CountriesDTO();
        c_dto.setId_country(c.get().getId_country());
        c_dto.setName_country(c.get().getName_country());
        c_dto.setAcronym_shop(c.get().getAcronym_shop());
        

        return c_dto;
    }

    @Override
    public CountriesDTO findCountryByNameCountry(String name_country) throws CustomException {

        CountriesDTO c_result = new CountriesDTO();
        List<CountriesEntity> allCountries = new ArrayList<>();
        allCountries = countriesRepository.findAll();
        CountriesEntity c_tmp = countriesUtils.getCountryByName(allCountries, name_country);
        c_result.setId_country(c_tmp.getId_country());
        c_result.setName_country(c_tmp.getName_country());
        c_result.setAcronym_shop(c_tmp.getAcronym_shop());

        if (c_result.getId_country() == null) {

            throw new CustomException("Country not present ", " please check the 'name_country' inserted into REQUEST");
        }

        return c_result;
}


}
