package com.example.microservice.service;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.repository.ProductsRepository;
import com.example.microservice.repository.ShopsRepository;
import com.example.microservice.utils.ProductsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductsRepository productsRepository;
    private ProductsUtils productsUtils;


    @Autowired
    public ProductServiceImpl(ProductsRepository productsRepository, ProductsUtils productsUtils, ShopsRepository shopsRepository){
        this.productsRepository = productsRepository;
        this.productsUtils = productsUtils;
    }






    //come chiamare una stored procedure da SpringBootApp con parametri
    @Override
    public String insertNewProductSP(String name_product, Integer code_product, Integer fk_shop) {

        productsUtils.sp_insertProductsCheckId(name_product, code_product, fk_shop);

        List<ProductsEntity> allProducts = allProducts();
        ProductsEntity pe = productsUtils.getLastProduct(allProducts);

        return "Perfect, you've already insert into DB:\n" + "ID_PRODUCT: " +  pe.getId_product() + "\n" + "NAME_PRODUCT: " + pe.getName_product() + "\n" + "CODE_PRODUCT: " + pe.getCode_product();
    }

    @Override
    public List<ProductsEntity> allProducts() {

        List<ProductsEntity> result = new ArrayList<>();
        result = productsRepository.findAll();
        result = productsUtils.orderListProductsByIdSTREAM(result);
        return result;
    }

    @Override
    public ProductsEntity findProductById(Integer id_product){

        Optional<ProductsEntity> peResult = productsRepository.findById(id_product);
        ProductsEntity peResultInObject = peResult.get();
        return peResultInObject;
    }

    @Override
    public List<ProductsEntity> deleteAndOrderProductsByIDsSP(Integer id_product) {

        productsUtils.sp_deleteUsers(id_product);
        productsUtils.sp_orderProductsIDS(id_product);

        List<ProductsEntity> allProducts = allProducts();
        return allProducts;
    }

    @Override
    public String updateAllProductsDataByIdSP(Integer id_product, String name_product, Integer code_product) {

        ProductsEntity productBeforeUpdate = findProductById(id_product);
        productsUtils.sp_updateProducts(id_product, name_product, code_product);

        return "You've already updated PRODUCT --> FROM:\n" + productBeforeUpdate.getName_product() + "\n" + productBeforeUpdate.getCode_product() + "\n" +
                "-->TO:\n" + name_product + "\n" + code_product;

    }

    @Override
    public String updateProductsDataByIdCheckNULLSP(Integer id_product, String name_product, Integer code_product) {

        ProductsEntity productBeforeUpdate = findProductById(id_product);
        productsUtils.sp_updateProductsCheckNull(id_product, name_product, code_product);
        ProductsEntity productsEntityAfterUpdate = findProductById(id_product);

        return "You've already updated PRODUCT --> FROM:\n" + productBeforeUpdate.getName_product() + "\n" + productBeforeUpdate.getCode_product() + "\n" +
                "-->TO:\n" + productsEntityAfterUpdate.getName_product() + "\n" + productsEntityAfterUpdate.getCode_product();

    }


}
