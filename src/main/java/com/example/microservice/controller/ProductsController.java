package com.example.microservice.controller;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.ProductsEntity;
import com.example.microservice.entity.ShopsEntity;
import com.example.microservice.service.ProductServiceImpl;
import com.example.microservice.utils.ShopsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:8080")
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
                                   @RequestParam("code_product")Integer code_product,
                                   @RequestParam("fk_shop")Integer fk_shop){

        return productServiceImpl.insertNewProduct(name_product, code_product, fk_shop);
    }

    @PostMapping(value = "/findProductById", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findProductById(@RequestParam("id_product")Integer id_product){

        ProductsEntity peRes = productServiceImpl.findProductById(id_product);
        return "Your ID is:\n" + peRes.getId_product() + "\n" + peRes.getName_product() + "\n" +peRes.getCode_product();
    }

   @DeleteMapping(value = "/deleteAndOrderProductById", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductsEntity> deleteAndOrderProductById(@RequestParam("id_product")Integer id_product){

       List<ProductsEntity> resultProductsList = productServiceImpl.deleteAndOrderProductsByIDs(id_product);
        return resultProductsList;
    }

    @PutMapping(value = "/updateAllDataProductById", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAllProductsDataById(@RequestParam("id_product")Integer id_product,
                                    @RequestParam("name_product")String name_product,
                                    @RequestParam("code_product")Integer code_product){


        return productServiceImpl.updateAllProductsDataById(id_product, name_product, code_product);
    }

    @PutMapping(value = "/updateDataProductCheckNULLById", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProductsDataCheckNULLById(@RequestParam("id_product")Integer id_product,
                                    @RequestParam("name_product")String name_product,
                                    @RequestParam("code_product")Integer code_product){


        return productServiceImpl.updateProductsDataByIdCheckNULL(id_product, name_product, code_product);
    }




    /*TO DO
    JOIN UNO TO MANY
    E DTO VARI
     */
}
