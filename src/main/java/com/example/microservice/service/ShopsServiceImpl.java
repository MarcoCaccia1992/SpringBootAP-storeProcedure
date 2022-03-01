package com.example.microservice.service;

import com.example.microservice.DTO.InnerJoinShopsProductsClassDTO;
import com.example.microservice.DTO.InnerJoinShopsProductsInterfaceDTO;
import com.example.microservice.DTO.ShopsDTO;
import com.example.microservice.entity.CountriesEntity;
import com.example.microservice.entity.ShopsEntity;
import com.example.microservice.repository.ShopsRepository;
import com.example.microservice.utils.JoinUtils;
import com.example.microservice.utils.ShopsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopsServiceImpl implements ShopsService{

    private ShopsUtils shopsUtils;
    private ShopsRepository shopsRepository;
    private JoinUtils joinUtils;

    @Autowired
    public ShopsServiceImpl(ShopsUtils shopsUtils, ShopsRepository shopsRepository, JoinUtils joinUtils){
        this.shopsUtils = shopsUtils;
        this.shopsRepository = shopsRepository;
        this.joinUtils = joinUtils;
    }





    public List<InnerJoinShopsProductsInterfaceDTO> getJoinShopsProductsNATIVEQUERY(){

        List<InnerJoinShopsProductsInterfaceDTO> result = shopsRepository.resultInnerJoinNATIVE();
        return result;
    }

    public List<InnerJoinShopsProductsClassDTO> getJoinShopsProductsJPQL(){

        List<InnerJoinShopsProductsClassDTO> result = shopsRepository.resultInnerJoinJPQL();
        return result;
    }

    public ShopsEntity getShopById(Integer id_shop){

        Optional<ShopsEntity> se = shopsRepository.findById(id_shop);
        ShopsEntity seOBJ = se.get();
        return seOBJ;
    }

    @Override
    public String insertNewShopSP(String name_shop, String region_code) {

        if(region_code == null) {
            region_code = "NOT ITALY REGION_CODE present on DB";
            shopsUtils.sp_insertShopsCheckId(name_shop, region_code);
        }
        if(region_code != null && region_code.equalsIgnoreCase("ve")){ // Veneto
            ShopsEntity seToInsertIntoDB = shopsUtils.checkRegionCodeAndJoinShopVE(name_shop, region_code);
            shopsRepository.save(seToInsertIntoDB);
        }
        if(region_code != null && region_code.equalsIgnoreCase("bs")){ // Basilicata
            ShopsEntity seToInsertIntoDB = shopsUtils.checkRegionCodeAndJoinShopBS(name_shop, region_code);
            shopsRepository.save(seToInsertIntoDB);
        }
        if(region_code != null && region_code.equalsIgnoreCase("pu")){ // Puglia
            ShopsEntity seToInsertIntoDB = shopsUtils.checkRegionCodeAndJoinShopPU(name_shop, region_code);
            shopsRepository.save(seToInsertIntoDB);
        }
        if(region_code != null && region_code.equalsIgnoreCase("ca")){ // Calabria
            ShopsEntity seToInsertIntoDB = shopsUtils.checkRegionCodeAndJoinShopCA(name_shop, region_code);
            shopsRepository.save(seToInsertIntoDB);
        }
        if(region_code != null && region_code.equalsIgnoreCase("lo")){ // Lombardia
            ShopsEntity seToInsertIntoDB = shopsUtils.checkRegionCodeAndJoinShopLO(name_shop, region_code);
            shopsRepository.save(seToInsertIntoDB);
        }
        List<ShopsEntity> allShopsUpdated = shopsRepository.findAll();
        ShopsEntity lastShopSaved = shopsUtils.getLastShop(allShopsUpdated);

        return "You've already insert:\n" + "ID_SHOP: " + lastShopSaved.getId_shop() + "\n"
                + "NAME_SHOP: " + lastShopSaved.getName_shop() + "\n"
                + "REGION_CODE: " + lastShopSaved.getRegion_code();
    } // TO-DO METTERE TUTTO IN UNA LISTA PER CHECCARE DIRETTAMENTE SE PRESENTE O MENO....

    @Override
    public List<ShopsDTO> getAllShopsWithoutJoin() {

        List<ShopsDTO> result = new ArrayList<>();
        result = shopsRepository.getAllShopsWithoutJoin();
        result = shopsUtils.orderListShopsDTOByIdSTREAM(result);
        return result;
    }

    @Override
    public String updateShopBySP(Integer id_shop, String name_shop, String region_code) {

        ShopsEntity seBeforeUpdate = shopsUtils.getShopById(id_shop);
        Map<String, Object> shopsAndCountries = shopsUtils.updateShopAndCheckRegionCode(id_shop, name_shop, region_code);
        ShopsEntity se = new ShopsEntity();
        CountriesEntity ce = new CountriesEntity();
        if(shopsAndCountries.containsKey("shop") && shopsAndCountries.containsKey("country")){
            for(Map.Entry<String, Object> obj : shopsAndCountries.entrySet()){
                if("shop".equalsIgnoreCase(obj.getKey())) {
                    se = (ShopsEntity) obj.getValue();
                    shopsRepository.save(se);
                }else if("country".equalsIgnoreCase(obj.getKey())){
                    ce = (CountriesEntity) obj.getValue();
                    shopsUtils.sp_insertCountriesCheckIdFromSU(ce.getName_country(), ce.getAcronym_shop());

                }
            }
        }

        String RegionCodeNotNUll = "You've already Updated\n" +
                "SHOP_ID: " + seBeforeUpdate.getId_shop() + "\n" +
                "NAME_SHOP: " + seBeforeUpdate.getName_shop() + "\n" +
                "REGION_CODE: " + seBeforeUpdate.getRegion_code() + "\n" +
                "\n-----INTO-----\n" +
                "\nSHOP_ID: " + id_shop + "\n" +
                "NAME_SHOP: " + name_shop + "\n" +
                "REGION_CODE: " + region_code;

        String RegionCodeNUll = "You've already Updated\n" +
                "SHOP_ID: " + seBeforeUpdate.getId_shop() + "\n" +
                "NAME_SHOP: " + seBeforeUpdate.getName_shop() + "\n" +
                "REGION_CODE: " + seBeforeUpdate.getRegion_code() + "\n" +
                "\n-----INTO-----\n" +
                "\nSHOP_ID: " + id_shop + "\n" +
                "NAME_SHOP: " + name_shop + "\n" +
                "REGION_CODE: " + seBeforeUpdate.getRegion_code();

        String result = null;

        if(region_code == null || region_code.isEmpty()){
            result = RegionCodeNUll;

        }else{
           result = RegionCodeNotNUll;
        }

        return result;
    }

    @Override
    public String updateShopByQUERY(Integer id_shop, String name_shop, String region_code) {

        shopsRepository.updateShopNATIVE(id_shop, name_shop, region_code);
        return "you've already updated\n" +
                "SHOP_ID: " + id_shop + " SUCCESSFULLY!";
    }

    @Override
    public String deleteShopAndOrderByIdSP(Integer id_shop) {

        ShopsEntity shopToDelete = shopsUtils.getShopById(id_shop);
        joinUtils.queryToDeleteRecordMTMBYId("id_shop", shopToDelete.getId_shop());
        //joinUtils.queryToDeleteRecordOTMShopProduct(shopToDelete.getId_shop()); // -->crere un metodo che controlla se dentro vi sono dei products con fk_shop valorizzato all'id dello shop corrispondente e nel caso cancellare
        shopsUtils.sp_deleteShopsById(id_shop);
        shopsUtils.sp_orderShopsById(id_shop);
        return "you've deleted the follow shop: \n" + shopToDelete.getId_shop() + "\n" + shopToDelete.getName_shop() + "\n" + shopToDelete.getRegion_code();
    }


}
