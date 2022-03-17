package com.example.microservice.entity;

import lombok.*;
import javax.persistence.*;

@Table(name="products")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ProductsEntity{

    @Id
    @Column(name="id_product")
    private Integer id_product;

    @Column(name="name_product")
    private String name_product;

    @Column(name="code_product")
    private Integer code_product;

    @Column(name="fk_shop")
    private Integer fk_shop;

}
