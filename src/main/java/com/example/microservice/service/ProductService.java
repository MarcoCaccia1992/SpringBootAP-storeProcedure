package com.example.microservice.service;


import com.example.microservice.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductService extends JpaRepository<ProductsEntity, Integer> {


}
