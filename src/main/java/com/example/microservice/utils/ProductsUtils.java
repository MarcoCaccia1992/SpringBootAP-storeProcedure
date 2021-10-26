package com.example.microservice.utils;

import com.example.microservice.entity.ProductsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Component
public class ProductsUtils {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductsUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }






//-----------------------------------------------STORED-PROCEDURE-----------------------------------------------

    public void sp_insertProductsCheckId(String name_product, Integer code_product){

        StoredProcedureQuery spQueryInsertProductsCheckId= entityManager.createStoredProcedureQuery("sp_insertProductsCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, name_product)
                .setParameter(2, code_product);

        spQueryInsertProductsCheckId.execute();
    }

    public void sp_deleteUsers(Integer id_product){

        StoredProcedureQuery spQueryDeleteUsers= entityManager.createStoredProcedureQuery("sp_deleteProducts")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, id_product);

        spQueryDeleteUsers.execute();
    }

    public void sp_orderProductsIDS(Integer id_product){

        StoredProcedureQuery spQueryOrderProductsIDS= entityManager.createStoredProcedureQuery("sp_orderProductsIDS")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, id_product);

        spQueryOrderProductsIDS.execute();
    }

    public void sp_updateProducts(Integer id_product, String name_product, Integer code_product){

        StoredProcedureQuery spQueryUpdateProductsByIDS= entityManager.createStoredProcedureQuery("sp_updateProducts")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .setParameter(1, id_product)
                .setParameter(2, name_product)
                .setParameter(3, code_product);

        spQueryUpdateProductsByIDS.execute();
    }

    public void sp_updateProductsCheckNull(Integer id_product, String name_product, Integer code_product){

        StoredProcedureQuery spQueryOrderProductsIDS= entityManager.createStoredProcedureQuery("sp_updateProductsCheckNull")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
                .setParameter(1, id_product)
                .setParameter(2, name_product)
                .setParameter(3, code_product);

        spQueryOrderProductsIDS.execute();
    }


//------------------------------------------------BUSINESS-LOGIC------------------------------------------------

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
