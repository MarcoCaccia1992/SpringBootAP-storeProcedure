package com.example.microservice.repository;

import com.example.microservice.DTO.InnerJoinShopsProductsClassDTO;
import com.example.microservice.DTO.InnerJoinShopsProductsInterfaceDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ShopsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopsRepository extends JpaRepository<ShopsEntity, Integer> {

    /* custom query example -->
    https://stackoverflow.com/questions/52591535/spring-jpa-no-converter-found-capable-of-converting-from-type*/
    @Query(value = "select shops.name_shop as nameShop, products.name_product as nameProduct\n" +
            "    from shops\n" +
            "    inner join products on shops.id_shop = products.fk_shop\n" +
            "    order  by shops.name_shop", nativeQuery = true)
    public List<InnerJoinShopsProductsInterfaceDTO> resultInnerJoinNATIVE();


    /* custom query JPQL utilizzando la parte di @JoinColumn e manyToOne*/
    @Query(value = "SELECT new com.example.microservice.DTO.InnerJoinShopsProductsClassDTO" +
                   "(s.name_shop, p.name_product) " +
                   "FROM ShopsEntity s JOIN s.productsEntityList p ORDER BY s.name_shop")
    public List<InnerJoinShopsProductsClassDTO> resultInnerJoinJPQL();

    @Query(value = "SELECT new com.example.microservice.DTO.ShopsDTO(s.id_shop, s.name_shop)" +
                   "FROM ShopsEntity s ORDER BY s.name_shop")
    public List<ShopsDTO> getAllShopsWithoutJoin();

}
