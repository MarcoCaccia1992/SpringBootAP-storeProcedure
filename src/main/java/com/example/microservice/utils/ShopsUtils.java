package com.example.microservice.utils;

import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ShopsEntity;
import com.example.microservice.mapper.MappingUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ShopsUtils {

    @PersistenceContext
    private EntityManager entityManager;

    private MappingUtils mappingUtils;

    @Autowired
    public ShopsUtils(EntityManager entityManager, MappingUtils mappingUtils) {
        this.entityManager = entityManager;
        this.mappingUtils = mappingUtils;
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

    public void sp_updateShops(Integer id_shop, String name_shop, String region_code) {

        StoredProcedureQuery spQueryInsertShopCheckId = entityManager.createStoredProcedureQuery("sp_updateShops")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .setParameter(1, id_shop)
                .setParameter(2, name_shop)
                .setParameter(3, region_code);

        spQueryInsertShopCheckId.execute();
    }

    public void sp_insertCountriesCheckIdFromSU(String name_country, String acronym_shop) {

        StoredProcedureQuery spQueryInsertCountryCheckId = entityManager.createStoredProcedureQuery("sp_insertCountriesCheckId")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .setParameter(1, name_country)
                .setParameter(2, acronym_shop);

        spQueryInsertCountryCheckId.execute();
    }

    public void sp_deleteShopsById(Integer id_shop) {

        StoredProcedureQuery spQueryDeleteById = entityManager.createStoredProcedureQuery("sp_deleteShops")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, id_shop);

        spQueryDeleteById.execute();
    }


//---------------------------------------------QUERY-ENTITYMANAGER---------------------------------------------


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

    public List<CountriesEntity> getAllCountriesEntityListUpdated() {

        List<CountriesEntity> ceList = new ArrayList<>();
        try {
            Query q = entityManager.createQuery("SELECT c FROM CountriesEntity c");
            ceList = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ceList;
    }


//-----------------------------------------------BUSINESS-LOGIC------------------------------------------------


    public ShopsEntity getLastShop(List<ShopsEntity> allShops) {

        ShopsEntity seResult = new ShopsEntity();
        if(!allShops.isEmpty()) {
            for (int i = 0; i < allShops.size(); i++) {
                if (i == allShops.size() - 1) {
                    seResult = allShops.get(i);
                }
            }
        }else{
            seResult = null;
        }

        return seResult;
    }

    public ShopsEntity getFirstShop(List<ShopsEntity> allShops) {

        ShopsEntity seResult = new ShopsEntity();
        for (int i = 0; i < allShops.size(); i++) {
            if (i == 0) {
                seResult = allShops.get(i);
            }
        }

        return seResult;
    }

    public ShopsEntity getShopById(Integer id_shop) {

        List<ShopsEntity> allShops = getAllShopsEntityListUpdated();
        ShopsEntity seResult = new ShopsEntity();
        for (int i = 0; i < allShops.size(); i++) {
            if (allShops.get(i).getId_shop() == id_shop) {
                seResult = allShops.get(i);
            }
        }

        return seResult;
    }

    public CountriesEntity getLastCountryFromSU(List<CountriesEntity> allCountries) {

        CountriesEntity ceResult = new CountriesEntity();
        for (int i = 0; i < allCountries.size(); i++) {
            if (i == allCountries.size() - 1) {
                ceResult = allCountries.get(i);
            }
        }

        return ceResult;
    }

    public CountriesEntity getCountryByNameCountryFromSU(List<CountriesEntity> allCountries, String country_name) {

        CountriesEntity ceResult = new CountriesEntity();
        if(!allCountries.isEmpty()) {
            for (int i = 0; i < allCountries.size(); i++) {
                if (allCountries.get(i).getName_country().equalsIgnoreCase(country_name)) {
                    ceResult = allCountries.get(i);
                    break;
                } else {
                    ceResult = null;
                }
            }
        }else{
            ceResult = null;
        }

        return ceResult;
    }

    public CountriesEntity getCountryByName(List<CountriesEntity> ceList, String name_country){

        CountriesEntity ce = new CountriesEntity();
        for(CountriesEntity cet : ceList){
            if(name_country.equalsIgnoreCase(cet.getName_country())){
                ce = cet;
            }
        }
        return ce;
    }

    public List<ShopsDTO> orderListShopsDTOByIdSTREAM(List<ShopsDTO> list) {

        List<ShopsDTO> sortedShops = list.stream()
                .sorted(Comparator.comparing(ShopsDTO::getId_shop)/*.reversed()*/)
                .collect(Collectors.toList());

        return sortedShops;
    }

    public List<ShopsEntity> orderListShopsEntityByIdSTREAM(List<ShopsEntity> list) {

        List<ShopsEntity> sortedShops = list.stream()
                .sorted(Comparator.comparing(ShopsEntity::getId_shop)/*.reversed()*/)
                .collect(Collectors.toList());

        return sortedShops;
    }

    public boolean checkRegionCodeList(String region_code) {

        List<String> regionCodeList = new ArrayList<>();
        boolean exit = false;
        regionCodeList.add("VE");
        regionCodeList.add("BS");
        regionCodeList.add("PU");
        regionCodeList.add("CA");
        regionCodeList.add("LO");

        for (int i = 0; i < regionCodeList.size(); i++) {
            if (regionCodeList.get(i).equalsIgnoreCase(region_code)) {
                exit = true;
            }
        }

        return exit;
    }

    public CountriesEntity createNewCountryByRegionCode(String region_code){

        CountriesEntity ce = new CountriesEntity();
        String checkRegion = null;
        if(region_code.equalsIgnoreCase("ve")){
            checkRegion = "Venezia";
            // get all countries already present on DB
            List<CountriesEntity> ceList = getAllCountriesEntityListUpdated();
            // return object by region_name parameter and take the last Country
            CountriesEntity lastCe = getLastCountryFromSU(ceList);
            CountriesEntity ceIfAlreadyExist = getCountryByNameCountryFromSU(ceList, checkRegion);
            if(ceIfAlreadyExist != null) {
                ce = mappingUtils.getMappedCountriesEntity(lastCe.getId_country() +1, checkRegion, "GDO");
            }else{
                ce = mappingUtils.getMappedCountriesEntity(1, "Venezia", "GDO");
            }
        }
        if(region_code.equalsIgnoreCase("bs")){
            checkRegion = "Basilicata";
            // get all countries already present on DB
            List<CountriesEntity> ceList = getAllCountriesEntityListUpdated();
            // return object by region_name parameter and take the last Country
            CountriesEntity lastCe = getLastCountryFromSU(ceList);
            CountriesEntity ceIfAlreadyExist = getCountryByNameCountryFromSU(ceList, checkRegion);
            if(ceIfAlreadyExist != null) {
                ce = mappingUtils.getMappedCountriesEntity(lastCe.getId_country() +1, checkRegion, "GDO");
            }else{
                ce = mappingUtils.getMappedCountriesEntity(1, checkRegion, "GDO");
            }
        }
        if(region_code.equalsIgnoreCase("pu")){// da reèlicare in tutti i casi
            checkRegion = "Puglia";
            // get all countries already present on DB
            List<CountriesEntity> ceList = getAllCountriesEntityListUpdated();
            // return object by region_name parameter and take the last Country
            CountriesEntity lastCe = getLastCountryFromSU(ceList);
            CountriesEntity ceIfAlreadyExist = getCountryByNameCountryFromSU(ceList, checkRegion);
            if(ceIfAlreadyExist != null || !ceList.isEmpty()) {
                ce = mappingUtils.getMappedCountriesEntity(lastCe.getId_country() +1, checkRegion, "GDO");
            }else if(ceIfAlreadyExist == null || ceList.isEmpty()){
                ce = mappingUtils.getMappedCountriesEntity(1, checkRegion, "GDO");
            }
        }
        if(region_code.equalsIgnoreCase("ca")){
            checkRegion = "Calabria";
            // get all countries already present on DB
            List<CountriesEntity> ceList = getAllCountriesEntityListUpdated();
            // return object by region_name parameter and take the last Country
            CountriesEntity lastCe = getLastCountryFromSU(ceList);
            CountriesEntity ceIfAlreadyExist = getCountryByNameCountryFromSU(ceList, checkRegion);
            if(ceIfAlreadyExist != null) {
                ce = mappingUtils.getMappedCountriesEntity(lastCe.getId_country() +1, checkRegion, "GDO");
            }else{
                ce = mappingUtils.getMappedCountriesEntity(1, checkRegion, "GDO");
            }
        }
        if(region_code.equalsIgnoreCase("lo")){
            checkRegion = "Lombardia";
            // get all countries already present on DB
            List<CountriesEntity> ceList = getAllCountriesEntityListUpdated();
            // return object by region_name parameter and take the last Country
            CountriesEntity lastCe = getLastCountryFromSU(ceList);
            CountriesEntity ceIfAlreadyExist = getCountryByNameCountryFromSU(ceList, checkRegion);
            if(ceIfAlreadyExist != null) {
                ce = mappingUtils.getMappedCountriesEntity(lastCe.getId_country() +1, checkRegion, "GDO");
            }else{
                ce = mappingUtils.getMappedCountriesEntity(1, checkRegion, "GDO");
            }
        }

        return ce;
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

    public ShopsEntity checkRegionCodeAndJoinShopVE(String name_shop, String region_code) {

        ShopsEntity se = new ShopsEntity();
        CountriesEntity ce = new CountriesEntity();
        String compareRegionCodeWithRegionName = null;
        if (checkRegionCodeList(region_code)) {
            if (region_code.equalsIgnoreCase("ve")) {
                //set the check String at corresponding RegionNAme
                compareRegionCodeWithRegionName = "Veneto";
                //create empty list to populate with DB countries table's data and get all countries datas in to the list
                List<CountriesEntity> allCountriesBeforeUpdate = getAllCountriesEntityListUpdated();
                //insert a new check, if RegionName is not present on DB, i create new Object to save into DB
                if (!ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate)) {
                    // create another Object to find the record where the country name it's like to "compareRegionCodeWithRegionName"
                    CountriesEntity CEbyNameCountry = getCountryByNameCountryFromSU(allCountriesBeforeUpdate, compareRegionCodeWithRegionName);
                    //populate the first countries Object and check if the ID is alredy present or not by CountryEntity OBJ
                    CountriesEntity CEAlreadyPresent = new CountriesEntity();
                    if(CEbyNameCountry == null && !allCountriesBeforeUpdate.isEmpty()){
                        CEAlreadyPresent = getLastCountryFromSU(allCountriesBeforeUpdate);
                        ce = mappingUtils.getMappedCountriesEntity(CEAlreadyPresent.getId_country() +1, "Veneto", "GDO");

                        sp_insertCountriesCheckIdFromSU(ce.getName_country(), ce.getAcronym_shop());
                    }
                    if(ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate) && CEbyNameCountry != null){
                        ce = mappingUtils.getMappedCountriesEntity(CEbyNameCountry.getId_country(), CEbyNameCountry.getName_country(), CEbyNameCountry.getAcronym_shop());
                    }

                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);
                    //and create populate new Shop Object with the ManyToMany join and insert all into DB + check if exist
                    if(lastShopBeforeUpdate != null) {
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }else{
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }

                }else{
                    // if already present the Country Object i create Shop Object only and insert that on DB
                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);

                    if(lastShopBeforeUpdate.getId_shop() == null || lastShopBeforeUpdate.getId_shop() == 0 ){
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);

                    }else{
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                    }

                    //create empty list to populate with DB countries table's data and get all countries datas in to the list
                    List<CountriesEntity> allCountriesBeforeUpdate1 = getAllCountriesEntityListUpdated();

                    CountriesEntity ce1 = getCountryByName(allCountriesBeforeUpdate1, compareRegionCodeWithRegionName);
                    se.addCountryToShop(ce1);

                }
            }
        }else{
            throw new IllegalArgumentException("The RegionCode does not exist in our DB!");
        }

        return se;
    }

    public ShopsEntity checkRegionCodeAndJoinShopBS(String name_shop, String region_code) {

        ShopsEntity se = new ShopsEntity();
        CountriesEntity ce = new CountriesEntity();
        String compareRegionCodeWithRegionName = null;
        if (checkRegionCodeList(region_code)) {
            if (region_code.equalsIgnoreCase("bs")) {
                //set the check String at corresponding RegionNAme
                compareRegionCodeWithRegionName = "Basilicata";
                //create empty list to populate with DB countries table's data and get all countries datas in to the list
                List<CountriesEntity> allCountriesBeforeUpdate = getAllCountriesEntityListUpdated();
                //insert a new check, if RegionName is not present on DB, i create new Object to save into DB
                if (!ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate)) {
                    // create another Object to find the record where the country name it's like to "compareRegionCodeWithRegionName"
                    CountriesEntity CEbyNameCountry = getCountryByNameCountryFromSU(allCountriesBeforeUpdate, compareRegionCodeWithRegionName);
                    //populate the first countries Object and check if the ID is alredy present or not by CountryEntity OBJ
                    CountriesEntity CEAlreadyPresent = new CountriesEntity();
                    if(CEbyNameCountry == null && !allCountriesBeforeUpdate.isEmpty()){
                        CEAlreadyPresent = getLastCountryFromSU(allCountriesBeforeUpdate);
                        ce = mappingUtils.getMappedCountriesEntity(CEAlreadyPresent.getId_country() +1, "Basilicata", "GDO");

                        sp_insertCountriesCheckIdFromSU(ce.getName_country(), ce.getAcronym_shop());
                    }
                    if(ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate) && CEbyNameCountry != null){
                        ce = mappingUtils.getMappedCountriesEntity(CEbyNameCountry.getId_country(), CEbyNameCountry.getName_country(), CEbyNameCountry.getAcronym_shop());
                    }

                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);
                    //and create populate new Shop Object with the ManyToMany join and insert all into DB + check if exist
                    if(lastShopBeforeUpdate != null) {
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }else{
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }

                }else{
                    // if already present the Country Object i create Shop Object only and insert that on DB
                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);

                    if(lastShopBeforeUpdate.getId_shop() == null || lastShopBeforeUpdate.getId_shop() == 0 ){
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);

                    }else{
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                    }

                    //create empty list to populate with DB countries table's data and get all countries datas in to the list
                    List<CountriesEntity> allCountriesBeforeUpdate1 = getAllCountriesEntityListUpdated();

                    CountriesEntity ce1 = getCountryByName(allCountriesBeforeUpdate1, compareRegionCodeWithRegionName);
                    se.addCountryToShop(ce1);

                }
            }
        }else{
            throw new IllegalArgumentException("The RegionCode does not exist in our DB!");
        }

        return se;
    }

    public ShopsEntity checkRegionCodeAndJoinShopPU(String name_shop, String region_code) {

        ShopsEntity se = new ShopsEntity();
        CountriesEntity ce = new CountriesEntity();
        String compareRegionCodeWithRegionName = null;
        if (checkRegionCodeList(region_code)) {
            if (region_code.equalsIgnoreCase("pu")) {
                //set the check String at corresponding RegionNAme
                compareRegionCodeWithRegionName = "Puglia";
                //create empty list to populate with DB countries table's data and get all countries datas in to the list
                List<CountriesEntity> allCountriesBeforeUpdate = getAllCountriesEntityListUpdated();
                //insert a new check, if RegionName is not present on DB, i create new Object to save into DB
                if (!ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate)) {
                    // create another Object to find the record where the country name it's like to "compareRegionCodeWithRegionName"
                    CountriesEntity CEbyNameCountry = getCountryByNameCountryFromSU(allCountriesBeforeUpdate, compareRegionCodeWithRegionName);
                    //populate the first countries Object and check if the ID is alredy present or not by CountryEntity OBJ
                    CountriesEntity CEAlreadyPresent = new CountriesEntity();
                    if(CEbyNameCountry == null && !allCountriesBeforeUpdate.isEmpty()){
                        CEAlreadyPresent = getLastCountryFromSU(allCountriesBeforeUpdate);
                        ce = mappingUtils.getMappedCountriesEntity(CEAlreadyPresent.getId_country() +1, "Puglia", "GDO");

                        sp_insertCountriesCheckIdFromSU(ce.getName_country(), ce.getAcronym_shop());
                    }
                    if(ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate) && CEbyNameCountry != null){
                        ce = mappingUtils.getMappedCountriesEntity(CEbyNameCountry.getId_country(), CEbyNameCountry.getName_country(), CEbyNameCountry.getAcronym_shop());
                    }

                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);
                    //and create populate new Shop Object with the ManyToMany join and insert all into DB + check if exist
                    if(lastShopBeforeUpdate != null) {
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }else{
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }

                }else{
                    // if already present the Country Object i create Shop Object only and insert that on DB
                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);

                    if(lastShopBeforeUpdate == null || lastShopBeforeUpdate.getId_shop() == null || lastShopBeforeUpdate.getId_shop() == 0){
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);

                    }else{
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                    }

                    //create empty list to populate with DB countries table's data and get all countries datas in to the list
                    List<CountriesEntity> allCountriesBeforeUpdate1 = getAllCountriesEntityListUpdated();

                    CountriesEntity ce1 = getCountryByName(allCountriesBeforeUpdate1, compareRegionCodeWithRegionName);
                    se.addCountryToShop(ce1);

                }
            }
        }else{
            throw new IllegalArgumentException("The RegionCode does not exist in our DB!");
        }

        return se;
    }

    public ShopsEntity checkRegionCodeAndJoinShopCA(String name_shop, String region_code) {

        ShopsEntity se = new ShopsEntity();
        CountriesEntity ce = new CountriesEntity();
        String compareRegionCodeWithRegionName = null;
        if (checkRegionCodeList(region_code)) {
            if (region_code.equalsIgnoreCase("ca")) {
                //set the check String at corresponding RegionNAme
                compareRegionCodeWithRegionName = "Calabria";
                //create empty list to populate with DB countries table's data and get all countries datas in to the list
                List<CountriesEntity> allCountriesBeforeUpdate = getAllCountriesEntityListUpdated();
                //insert a new check, if RegionName is not present on DB, i create new Object to save into DB
                if (!ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate)) {
                    // create another Object to find the record where the country name it's like to "compareRegionCodeWithRegionName"
                    CountriesEntity CEbyNameCountry = getCountryByNameCountryFromSU(allCountriesBeforeUpdate, compareRegionCodeWithRegionName);
                    //populate the first countries Object and check if the ID is alredy present or not by CountryEntity OBJ
                    CountriesEntity CEAlreadyPresent = new CountriesEntity();
                    if(CEbyNameCountry == null && !allCountriesBeforeUpdate.isEmpty()){
                        CEAlreadyPresent = getLastCountryFromSU(allCountriesBeforeUpdate);
                        ce = mappingUtils.getMappedCountriesEntity(CEAlreadyPresent.getId_country() +1, "Calabria", "GDO");

                        sp_insertCountriesCheckIdFromSU(ce.getName_country(), ce.getAcronym_shop());
                    }
                    if(ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate) && CEbyNameCountry != null){
                        ce = mappingUtils.getMappedCountriesEntity(CEbyNameCountry.getId_country(), CEbyNameCountry.getName_country(), CEbyNameCountry.getAcronym_shop());
                    }

                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);
                    //and create populate new Shop Object with the ManyToMany join and insert all into DB + check if exist
                    if(lastShopBeforeUpdate != null) {
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }else{
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }

                }else{
                    // if already present the Country Object i create Shop Object only and insert that on DB
                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);

                    if(lastShopBeforeUpdate.getId_shop() == null || lastShopBeforeUpdate.getId_shop() == 0 ){
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);

                    }else{
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                    }

                    //create empty list to populate with DB countries table's data and get all countries datas in to the list
                    List<CountriesEntity> allCountriesBeforeUpdate1 = getAllCountriesEntityListUpdated();

                    CountriesEntity ce1 = getCountryByName(allCountriesBeforeUpdate1, compareRegionCodeWithRegionName);
                    se.addCountryToShop(ce1);

                }
            }
        }else{
            throw new IllegalArgumentException("The RegionCode does not exist in our DB!");
        }

        return se;
    }

    public ShopsEntity checkRegionCodeAndJoinShopLO(String name_shop, String region_code) {

        ShopsEntity se = new ShopsEntity();
        CountriesEntity ce = new CountriesEntity();
        String compareRegionCodeWithRegionName = null;

        if (checkRegionCodeList(region_code)) {
            if (region_code.equalsIgnoreCase("lo")) {
                //set the check String at corresponding RegionNAme
                compareRegionCodeWithRegionName = "Lombardia";
                //create empty list to populate with DB countries table's data and get all countries datas in to the list
                List<CountriesEntity> allCountriesBeforeUpdate = getAllCountriesEntityListUpdated();
                //insert a new check, if RegionName is not present on DB, i create new Object to save into DB
                if (!ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate)) {
                    // create another Object to find the record where the country name it's like to "compareRegionCodeWithRegionName"
                    CountriesEntity CEbyNameCountry = getCountryByNameCountryFromSU(allCountriesBeforeUpdate, compareRegionCodeWithRegionName);
                    //populate the first countries Object and check if the ID is already present or not by CountryEntity OBJ
                    CountriesEntity CEAlreadyPresent;
                    if(CEbyNameCountry == null && !allCountriesBeforeUpdate.isEmpty()){
                        CEAlreadyPresent = getLastCountryFromSU(allCountriesBeforeUpdate);
                        ce = mappingUtils.getMappedCountriesEntity(CEAlreadyPresent.getId_country() +1, "Lombardia", "GDO");

                        sp_insertCountriesCheckIdFromSU(ce.getName_country(), ce.getAcronym_shop());
                    }
                    if(CEbyNameCountry == null && allCountriesBeforeUpdate.isEmpty()){
                        ce = mappingUtils.getMappedCountriesEntity(1, "Lombardia", "GDO");

                        sp_insertCountriesCheckIdFromSU(ce.getName_country(), ce.getAcronym_shop());
                    }
                    if(ifAlreadyPresent(compareRegionCodeWithRegionName, allCountriesBeforeUpdate) && CEbyNameCountry != null){
                        ce = mappingUtils.getMappedCountriesEntity(CEbyNameCountry.getId_country(), CEbyNameCountry.getName_country(), CEbyNameCountry.getAcronym_shop());
                    }

                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);
                    //and create populate new Shop Object with the ManyToMany join and insert all into DB + check if exist

                    if(lastShopBeforeUpdate != null) {
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }else{
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);
                        se.addCountryToShop(ce);
                    }

                }else{
                    // if already present the Country Object i create Shop Object only and insert that on DB
                    List<ShopsEntity> shopsEntityListBeforeUpdate = getAllShopsEntityListUpdated();
                    shopsEntityListBeforeUpdate = orderListShopsEntityByIdSTREAM(shopsEntityListBeforeUpdate);
                    ShopsEntity lastShopBeforeUpdate = getLastShop(shopsEntityListBeforeUpdate);

                    if(lastShopBeforeUpdate == null || lastShopBeforeUpdate.getId_shop() == 0 ){
                        se = mappingUtils.getMappedShopsEntity(1, name_shop, region_code);

                    }else{
                        se = mappingUtils.getMappedShopsEntity(lastShopBeforeUpdate.getId_shop() + 1, name_shop, region_code);
                    }

                    //create empty list to populate with DB countries table's data and get all countries datas in to the list
                    List<CountriesEntity> allCountriesBeforeUpdate1 = getAllCountriesEntityListUpdated();

                    CountriesEntity ce1 = getCountryByName(allCountriesBeforeUpdate1, compareRegionCodeWithRegionName);
                    se.addCountryToShop(ce1);

                }
            }
        }else{
            throw new IllegalArgumentException("The RegionCode does not exist in our DB!");
        }

        return se;
    }

    public Map<String, Object> updateShopAndCheckRegionCode(Integer id_shop, String name_shop, String region_code){

        ShopsEntity seBeforeUpdate = getShopById(id_shop);
        ShopsEntity seToUpdate = new ShopsEntity();
        Map<String, Object> resultMap = new HashMap<>();
        if(region_code == null || region_code.isEmpty()){
            sp_updateShops(id_shop, name_shop, seBeforeUpdate.getRegion_code());

        }else{
            if(seBeforeUpdate.getRegion_code() != region_code && checkRegionCodeList(region_code)){
                CountriesEntity ce = createNewCountryByRegionCode(region_code);
                // da vedere se con la save lo sovrascrive possibile erroe
                seToUpdate = getShopById(id_shop);
                seToUpdate.setName_shop(name_shop);
                seToUpdate.setRegion_code(region_code);
                seToUpdate.addCountryToShop(ce);
                resultMap.put("shop", seToUpdate);
                resultMap.put("country", ce);

            }else{
                sp_updateShops(id_shop, name_shop, region_code);
                seToUpdate.setId_shop(id_shop);
                seToUpdate.setName_shop(name_shop);
                seToUpdate.setRegion_code(region_code);
                resultMap.put("shop", seToUpdate);
            }
        }
        return resultMap;
    }


}







