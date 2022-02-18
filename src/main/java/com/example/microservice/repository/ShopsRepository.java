package com.example.microservice.repository;

import com.example.microservice.DTO.InnerJoinShopsProductsClassDTO;
import com.example.microservice.DTO.InnerJoinShopsProductsInterfaceDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ShopsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ShopsRepository extends JpaRepository<ShopsEntity, Integer> {

    /* custom query example -->
    https://stackoverflow.com/questions/52591535/spring-jpa-no-converter-found-capable-of-converting-from-type*/
    @Query(value = "SELECT shops.name_shop AS nameShop, products.name_product AS nameProduct\n" +
            "    FROM shops\n" +
            "    INNER JOIN products ON shops.id_shop = products.fk_shop\n" +
            "    ORDER  BY shops.name_shop", nativeQuery = true)
    public List<InnerJoinShopsProductsInterfaceDTO> resultInnerJoinNATIVE();


    /* custom query JPQL utilizzando la parte di @JoinColumn e manyToOne*/
    @Query(value = "SELECT new com.example.microservice.DTO.InnerJoinShopsProductsClassDTO" +
                   "(s.name_shop, p.name_product) " +
                   "FROM ShopsEntity s JOIN s.productsEntityList p ORDER BY s.name_shop")
    public List<InnerJoinShopsProductsClassDTO> resultInnerJoinJPQL();

    @Query(value = "SELECT new com.example.microservice.DTO.ShopsDTO(s.id_shop, s.name_shop, s.region_code)" +
                   "FROM ShopsEntity s ORDER BY s.name_shop")
    public List<ShopsDTO> getAllShopsWithoutJoin();

    @Modifying
    @Transactional
    @Query(value = "UPDATE shops s SET s.name_shop = :name_shop, " +
            "s.region_code = :region_code  WHERE s.id_shop = :id_shop", nativeQuery = true)
    public void  updateShopNATIVE(@Param("id_shop")Integer id_shop,
                                    @Param("name_shop")String name_shop,
                                    @Param("region_code")String region_code);

}
