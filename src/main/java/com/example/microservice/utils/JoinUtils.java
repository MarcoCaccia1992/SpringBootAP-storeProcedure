package com.example.microservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.transaction.Transactional;

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

    @Transactional
    @Modifying
    public void queryToDeleteRecordMTMBYId(String column , Integer id_to_delete){

        Query test =  entityManager.createNativeQuery("DELETE FROM country_shop_join_mtm WHERE "
                + id_to_delete + " IN (SELECT " + column + " FROM country_shop_join_mtm WHERE "
                + column + " = " + id_to_delete + ")");
        test.executeUpdate();

    }
}
