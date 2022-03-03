package com.example.microservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProductsDTO {

    private String name_product;
    private Integer code_product;
    private Integer fk_shop;

}
