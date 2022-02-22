package com.example.microservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Component
public class JoinUtils {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public JoinUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }





//-----------------------------------------------STORED-PROCEDURE-----------------------------------------------

    public void sp_deleteFrom_country_shop_join_mtm(Integer id_country){

        StoredProcedureQuery spQueryDeleteFrom_country_shop_join_mtm= entityManager.createStoredProcedureQuery("sp_deleteCountry_shop_join_mtm")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, id_country);

        spQueryDeleteFrom_country_shop_join_mtm.execute();
    }
}
