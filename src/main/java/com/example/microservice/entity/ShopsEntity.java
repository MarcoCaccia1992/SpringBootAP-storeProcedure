package com.example.microservice.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name="shops")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ShopsEntity {

    @Id
    @Column(name="id_shop")
    private Integer id_shop;

    @Column(name="name_shop")
    private String name_shop;

    @Column(name="region_code")
    private String region_code;

    /* in questo caso la ManyToMany fa da riferimento alla classe CountryEntity come mappatura
    * per i cmapi e non l'annotation @JoinTable gli si danno i riferimenti per la tablle che dovrà
    * cotruire a runtime con le DUE FOREIGNKEY per urine le due tabelle
     */
    @ManyToMany
    @JoinTable(
            name = "country_shop_join_mtm",
            joinColumns = @JoinColumn(name = "id_shop"),
            inverseJoinColumns = @JoinColumn(name = "id_country")
    )
    private List<CountriesEntity> countryToShop = new ArrayList<>();

    public ShopsEntity(String name_shop, String region_code){
        this.name_shop = name_shop;
        this.region_code = region_code;
    }

    public void addCountryToShop(CountriesEntity countriesEntity){
        countryToShop.add(countriesEntity);
    }

    @OneToMany(targetEntity = ProductsEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_shop", referencedColumnName = "id_shop")
    private List<ProductsEntity> productsEntityList;



}
