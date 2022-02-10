package com.example.microservice.repository;

import com.example.microservice.DTO.CountriesDTO;
import com.example.microservice.entity.CountriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountriesRepository extends JpaRepository<CountriesEntity, Integer> {

    //JPQL
    @Query(value = "SELECT new com.example.microservice.DTO.CountriesDTO" +
            "(c.id_country, c.name_country, c.acronym_shop)" +
            "FROM CountriesEntity c ORDER BY c.name_country")
    public List<CountriesDTO> getAllCountriesWithoutJoin();
}
