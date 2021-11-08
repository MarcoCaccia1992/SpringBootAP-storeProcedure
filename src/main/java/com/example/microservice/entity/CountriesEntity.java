package com.example.microservice.entity;

import com.example.microservice.DTO.ShopsDTO;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name="countries")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CountriesEntity {

    @Id
    @Column(name="id_country")
    private Integer id_country;

    @Column(name="name_country")
    private String name_country;

    @Column(name="acronym_shop")
    private String acronym_shop;


}
