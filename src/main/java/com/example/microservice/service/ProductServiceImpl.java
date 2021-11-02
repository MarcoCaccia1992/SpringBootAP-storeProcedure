package com.example.microservice.service;

import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.repository.ProductsRepository;
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
    public ProductServiceImpl(ProductsRepository productsRepository, ProductsUtils productsUtils){
        this.productsRepository = productsRepository;
        this.productsUtils = productsUtils;
    }






    //come chiamare una stored procedure da SpringBootApp con parametri
    @Override
    public String insertNewProduct(String name_product, Integer code_product) {

        productsUtils.sp_insertProductsCheckId(name_product, code_product);

        List<ProductsEntity> allProducts = allProducts();
        ProductsEntity pe = productsUtils.getLastProduct(allProducts);

        return "Perfect, you've already insert into DB:\n" + pe.getId_product() + "\n" + pe.getName_product() + "\n" + pe.getCode_product();
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
    public List<ProductsEntity> deleteAndOrderProductsByIDs(Integer id_product) {

        productsUtils.sp_deleteUsers(id_product);
        productsUtils.sp_orderProductsIDS(id_product);

        List<ProductsEntity> allProducts = allProducts();
        return allProducts;
    }

    @Override
    public String updateAllProductsDataById(Integer id_product, String name_product, Integer code_product) {

        ProductsEntity productBeforeUpdate = findProductById(id_product);
        productsUtils.sp_updateProducts(id_product, name_product, code_product);
        ProductsEntity productsEntityAfterUpdate = findProductById(id_product);

        return "You've already updated PRODUCT --> FROM:\n" + productBeforeUpdate.getName_product() + "\n" + productBeforeUpdate.getCode_product() + "\n" +
                "-->TO:\n" + productsEntityAfterUpdate.getName_product() + "\n" + productsEntityAfterUpdate.getCode_product();

    }

    @Override
    public String updateProductsDataByIdCheckNULL(Integer id_product, String name_product, Integer code_product) {

        ProductsEntity productBeforeUpdate = findProductById(id_product);
        productsUtils.sp_updateProductsCheckNull(id_product, name_product, code_product);
        ProductsEntity productsEntityAfterUpdate = findProductById(id_product);

        return "You've already updated PRODUCT --> FROM:\n" + productBeforeUpdate.getName_product() + "\n" + productBeforeUpdate.getCode_product() + "\n" +
                "-->TO:\n" + productsEntityAfterUpdate.getName_product() + "\n" + productsEntityAfterUpdate.getCode_product();

    }


}
