package com.example.microservice.utils;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ShopsEntity;
import com.example.microservice.repository.ShopsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ShopsUtils {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ShopsUtils(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


//-----------------------------------------------STORED-PROCEDURE-----------------------------------------------


    public void sp_insertShopsCheckId(String name_shop, String region_code) {

        StoredProcedureQuery spQueryInsertShopCheckId = entityManager.createStoredProcedureQuery("sp_insertShopsCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .setParameter(1, name_shop)
                .setParameter(2, region_code);

        spQueryInsertShopCheckId.execute();
    }

    public void sp_insertCountriesCheckId(String name_country, String acronym_shop) {

        StoredProcedureQuery spQueryInsertCountryCheckId = entityManager.createStoredProcedureQuery("sp_insertCountriesCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .setParameter(1, name_country)
                .setParameter(2, acronym_shop);

        spQueryInsertCountryCheckId.execute();
    }


//-----------------------------------------------BUSINESS-LOGIC------------------------------------------------


    public ShopsEntity getLastShop(List<ShopsEntity> allShops) {

        ShopsEntity seResult = new ShopsEntity();
        for (int i = 0; i < allShops.size(); i++) {
            if (i == allShops.size() - 1) {
                seResult = allShops.get(i);
            }
        }

        return seResult;
    }

    public CountriesEntity getLastCountryFromSP(List<CountriesEntity> allCountries) {

        CountriesEntity ceResult = new CountriesEntity();
        for (int i = 0; i < allCountries.size(); i++) {
            if (i == allCountries.size() - 1) {
                ceResult = allCountries.get(i);
            }
        }

        return ceResult;
    }

    public CountriesEntity getCountryByNameCountryFromSP(List<CountriesEntity> allCountries, String country_name) {

        CountriesEntity ceResult = new CountriesEntity();
        for (int i = 0; i < allCountries.size(); i++) {
            if (allCountries.get(i).getName_country().equalsIgnoreCase(country_name)) {
                ceResult = allCountries.get(i);
            }
        }

        return ceResult;
    }

    public List<ShopsDTO> orderListShopsByIdSTREAM(List<ShopsDTO> list) {

        List<ShopsDTO> sortedShops = list.stream()
                .sorted(Comparator.comparing(ShopsDTO::getId_shop)/*.reversed()*/)
                .collect(Collectors.toList());

        return sortedShops;
    }

    public List<ShopsEntity> getAllShopsEntityListUpdated() {

        List<ShopsEntity> seList = new ArrayList<>();
        try {
            Query q = entityManager.createQuery("SELECT s FROM ShopsEntity s");// sintassi corretta della normale SELECT * FROM tabl
            seList = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seList;
    }

    public ShopsEntity checkRegionCodeAndJoinShop(String name_shop, String region_code) {

        ShopsEntity se = new ShopsEntity();
        CountriesEntity ce = new CountriesEntity();
        String compareRegionCodeWithRegionName = null;
        if (checkRegionCodeList(region_code)) {
            if (region_code.equalsIgnoreCase("lo")) {
                //set the check String at corresponding RegionNAme
                compareRegionCodeWithRegionName = "Lombardia";
                //create empty list to populate with DB countries table's data
                List<CountriesEntity> allCountriesBeforeUpdate = new ArrayList<>();
                //get all countries datas in to the list
                try {
                    Query q = entityManager.createQuery("SELECT c FROM CountriesEntity c");
                    allCountriesBeforeUpdate = q.getResultList();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //insert a new check, if RegionName is not present on DB, i create new Object to save into DB
                if (!ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate)) {
                    // create another Object to find the record where the country name it's like to "compareRegionCodeWithRegionName"
                    CountriesEntity CEbyNameCountry = getCountryByNameCountryFromSP(allCountriesBeforeUpdate, compareRegionCodeWithRegionName);// possibile errore perchè devo creare l'oggetto con il campo preso, in questo caso "Lombardia"
                    //populate the first countries Object and check if hte ID is alredy present or not
                    if(CEbyNameCountry.getId_country() == null || CEbyNameCountry.getId_country() == 0 || CEbyNameCountry == null){
                        ce.setId_country(1);
                        ce.setName_country("Lombardia");
                        ce.setAcronym_shop("GDO");

                        sp_insertCountriesCheckId(ce.getName_country(), ce.getAcronym_shop());
                    }
                    if(ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate) && CEbyNameCountry != null){// continuare da qui per gestire la nullpointer nel caso ci sia o non ci sia il campo in countries.
                        ce = CEbyNameCountry;
                    }

                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);
                    //and create populate new Shop Object with the ManyToMany join and insert all into DB + check if exist
                    if(lastShopBeforeUpdate != null) {
                        se.setId_shop(lastShopBeforeUpdate.getId_shop() + 1);
                    }else{
                        se.setId_shop(1);
                    }
                    se.setName_shop(name_shop);
                    se.setRegion_code(region_code);
                    se.addCountryToShop(ce);

                }else{
                    // if already present the Country Object i create Shop Object only and insert that on DB
                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);

                    if(lastShopBeforeUpdate.getId_shop() == null || lastShopBeforeUpdate.getId_shop() == 0 ){
                        se.setId_shop(1);
                    }else {
                        se.setId_shop(lastShopBeforeUpdate.getId_shop() + 1);
                    }

                    //create empty list to populate with DB countries table's data
                    List<CountriesEntity> allCountriesBeforeUpdate1 = new ArrayList<>();
                    //get all countries datas in to the list
                    try {
                        Query q = entityManager.createQuery("SELECT c FROM CountriesEntity c");
                        allCountriesBeforeUpdate1 = q.getResultList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    CountriesEntity ce1 = getLastCountryFromSP(allCountriesBeforeUpdate1);

                    se.setName_shop(name_shop);
                    se.setRegion_code(region_code);
                    se.addCountryToShop(ce1);

                }
            }
        }

        return se;
    }

    public boolean checkRegionCodeList(String region_code) {

        List<String> regionCodeList = new ArrayList<>();
        boolean exit = false;
        regionCodeList.add("AG");
        regionCodeList.add("AP");
        regionCodeList.add("BS");
        regionCodeList.add("FE");
        regionCodeList.add("LC");
        regionCodeList.add("LO");

        for (int i = 0; i < regionCodeList.size(); i++) {
            if (regionCodeList.get(i).equalsIgnoreCase(region_code)) {
                exit = true;
            }
        }

        return exit;
    }

    public boolean ifAlreadyPresent(String name_country, List<CountriesEntity> allCountries) {

        boolean exit = false;
        for (int i = 0; i < allCountries.size(); i++) {
            if (allCountries.get(i).getName_country().equalsIgnoreCase(name_country)) {
                exit = true;
            }
        }

        return exit;
    }

}







