package com.example.microservice.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // mappata dalla tabella per aggiungere l'oggetto all'interno della tabella SHOPS dentro ShopsEntity
    @ManyToMany(mappedBy = "countryToShop")
    private List<ShopsEntity> shopToCountry = new ArrayList<>();

    public CountriesEntity(String name_country, String acronym_shop){

        this.name_country = name_country;
        this.acronym_shop = acronym_shop;
    }

    public void addShopToCountry(ShopsEntity shopsEntity){

        shopToCountry.add(shopsEntity);
    }


}
