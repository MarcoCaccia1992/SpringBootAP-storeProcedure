package com.example.microservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CountriesDTO {

    private Integer id_country;
    private String name_country;
    private String acronym_shop;
}
