package com.example.microservice.entity;

import lombok.*;
import javax.persistence.*;
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

    @OneToMany(targetEntity = ProductsEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_shop", referencedColumnName = "id_shop")
    private List<ProductsEntity> productsEntityList;

    // in questo modo si crea direttamnte a db una tabella che abbia come primary key i due id che servono per poi effettuare gli incroci
    @ManyToMany
    @JoinTable(
            name = "countryshop",
            joinColumns = @JoinColumn(name = "id_shop"),
            inverseJoinColumns = @JoinColumn(name = "id_country")
    )
    private Set<CountriesEntity> countryShop = new HashSet<>();

}
