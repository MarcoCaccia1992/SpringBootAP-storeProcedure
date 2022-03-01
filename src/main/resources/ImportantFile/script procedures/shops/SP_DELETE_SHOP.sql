create or replace PROCEDURE sp_deleteShops(
v_id shops.id_shop%TYPE)
AS
BEGIN
    DELETE FROM country_shop_join_mtm WHERE v_id = country_shop_join_mtm.id_shop;
    DELETE FROM products WHERE v_id = products.fk_shop;
    DELETE FROM shops WHERE v_id = shops.id_shop;
END;