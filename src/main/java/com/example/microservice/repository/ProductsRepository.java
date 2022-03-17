package com.example.microservice.repository;

import com.example.microservice.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "SELECT products p WHERE p.fk_shop = :id_shop", nativeQuery = true)
    List<ProductsEntity> getAllProductByFK_SHOP(@Param("id_shop")Integer id_shop);
}
