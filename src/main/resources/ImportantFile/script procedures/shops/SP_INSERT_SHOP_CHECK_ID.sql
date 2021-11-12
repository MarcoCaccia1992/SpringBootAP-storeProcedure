create or replace PROCEDURE sp_insertShopsCheckId(
v_name_shop shops.name_shop%TYPE,
v_region_code shops.region_code%TYPE)
AS
BEGIN
    INSERT INTO shops VALUES((maxId_shops_function + 1), 
    v_name_shop, v_region_code);
END;