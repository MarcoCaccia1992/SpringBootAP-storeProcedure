package com.example.microservice.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

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

    @OneToMany(targetEntity = ProductsEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_shop", referencedColumnName = "id_shop")
    private List<ProductsEntity> productsEntityList;
}
