create or replace PROCEDURE sp_updateShops(
V_id_shop IN shops.id_shop%TYPE,
V_name_shop IN shops.name_shop%TYPE)
AS
BEGIN

    UPDATE shops s
    SET s.name_shop = V_name_shop
    WHERE s.id_shop = V_id_shop;

END;