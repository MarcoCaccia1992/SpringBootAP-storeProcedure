package com.example.microservice.service;

import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.repository.ProductsRepository;
import com.example.microservice.utils.ProductsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductsRepository productsRepository;

    private ProductsUtils productsUtils;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductServiceImpl(ProductsRepository productsRepository, ProductsUtils productsUtils, EntityManager entityManager){
        this.productsRepository = productsRepository;
        this.productsUtils = productsUtils;
        this.entityManager = entityManager;
    }






    //come chiamare una stored procedure da SpringBootApp con parametri
    @Override
    public String insertNewProduct(String name_product, Integer code_product) {

        StoredProcedureQuery spQuery= entityManager.createStoredProcedureQuery("sp_insertProductsCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, name_product)
                .setParameter(2, code_product);

        spQuery.execute();

        List<ProductsEntity> allProducts = allProducts();
        ProductsEntity pe = productsUtils.getLastProduct(allProducts);

        return "Perfect, you've already insert into DB:\n" + pe.getId_product() + "\n" + pe.getName_product() + "\n" + pe.getCode_product();
    }

    @Override
    public List<ProductsEntity> allProducts() {

        List<ProductsEntity> result = new ArrayList<>();
        result = productsRepository.findAll();
        return result;
    }

    @Override
    public ProductsEntity findProductById(Integer id_product){

        Optional<ProductsEntity> peResult = productsRepository.findById(id_product);
        ProductsEntity peResultInObject = peResult.get();
        return peResultInObject;
    }



}
