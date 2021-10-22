package com.example.microservice.controller;

import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductsController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }






    @GetMapping(value = "/allProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductsEntity> getAllProducts(){

        return productServiceImpl.allProducts();
    }

    @PostMapping(value = "/newProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public String insertNewProduct(@RequestParam("name_product")String name_product,
                                   @RequestParam("code_product")Integer code_product){

        return productServiceImpl.insertNewProduct(name_product, code_product);
    }

    @PostMapping(value = "/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findById(@RequestParam("id_product")Integer id_product){

        ProductsEntity peRes = productServiceImpl.findProductById(id_product);
        return "Your ID is:\n" + peRes.getId_product() + "\n" + peRes.getName_product() + "\n" +peRes.getCode_product();
    }

   /* @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteById(@RequestParam("id_product")Integer id_product){

        ProductsEntity peRes = productServiceImpl.findProductById(id_product);
        return "You've already deleted this ID:\n" + peRes.getId_product() + "\n" + peRes.getName_product() + "\n" +peRes.getCode_product();
    }*/




    //TO DO
    /*DELETE
    PUT
    JOIN UNO TO MANY
    E DTO VARI
     */
}
