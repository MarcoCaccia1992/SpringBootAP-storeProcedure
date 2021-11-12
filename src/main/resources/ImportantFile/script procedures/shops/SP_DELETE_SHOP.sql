create or replace PROCEDURE sp_deleteShops(
v_id shops.id_shop%TYPE)
AS
BEGIN
    DELETE FROM shops WHERE v_id = shops.id_shop;
END;