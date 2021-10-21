package com.example.microservice.test;

import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.service.ProductService;
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
public class ProductServiceImpl {


    @Autowired
    private ProductService productService;

    @PersistenceContext
    private EntityManager em;






    //come chiamare una stored procedure da SpringBootApp con parametri
    public String insertNewProduct(String name_product, Integer code_product) {

        StoredProcedureQuery spQuery= em.createStoredProcedureQuery("sp_insertProductsCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, name_product)
                .setParameter(2, code_product);

        spQuery.execute();

        ProductsEntity pe = getLastProduct();

        return "Perfect, you've already insert into DB:\n" + pe.getId_product() + "\n" + pe.getName_product() + "\n" + pe.getCode_product();
    }

    public List<ProductsEntity> allProducts() {

        List<ProductsEntity> result = new ArrayList<>();
        result = productService.findAll();
        return result;
    }

    public ProductsEntity findProductById(Integer id_product){

        Optional<ProductsEntity> peResult = productService.findById(id_product);
        ProductsEntity peResultInObject = peResult.get();
        return peResultInObject;
    }

    public ProductsEntity getLastProduct(){

        ProductsEntity peResult = new ProductsEntity();
        List<ProductsEntity> allProducts = allProducts();
        for(int i=0; i<allProducts.size(); i++){
            if(i == allProducts.size() -1){
                peResult = allProducts.get(i);
            }
        }

        return peResult;
    }

}
