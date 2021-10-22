package com.example.microservice.utils;

import com.example.microservice.entity.ProductsEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductsUtils {






    public ProductsEntity getLastProduct(List<ProductsEntity> allProducts){

        ProductsEntity peResult = new ProductsEntity();
        for(int i=0; i<allProducts.size(); i++){
            if(i == allProducts.size() -1){
                peResult = allProducts.get(i);
            }
        }

        return peResult;
    }
}
