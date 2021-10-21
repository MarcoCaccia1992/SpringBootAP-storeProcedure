package com.example.microservice.controller;

import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.test.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductsController(ProductServiceImpl productServiceImpl){
        this.productServiceImpl = productServiceImpl;
    }






    @GetMapping("/allProducts")
    public List<ProductsEntity> getAllProducts(){
        return productServiceImpl.allProducts();
    }

    @PostMapping("/newProduct")
    public String insertNewProduct(@RequestParam("name_product")String name_product,
                                   @RequestParam("code_product")Integer code_product){

        return productServiceImpl.insertNewProduct(name_product, code_product);
    }

    @PostMapping("/findById")
    public String findById(@RequestParam("id_product")Integer id_product){

        ProductsEntity peRes = productServiceImpl.findProductById(id_product);
        return "Your ID is:\n" + peRes.getId_product() + "\n" + peRes.getName_product() + "\n" +peRes.getCode_product();
    }


    //TO DO
    /*DELETE
    PUT
    JOIN UNO TO MANY
    E DTO VARI
     */
}
