package com.example.microservice.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ShopsDTO {

    private Integer id_shop;
    private String name_shop;
    private String region_code;
}
