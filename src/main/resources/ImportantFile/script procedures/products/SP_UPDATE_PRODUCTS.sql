create or replace PROCEDURE sp_updateProducts(
V_id_product IN products.id_product%TYPE,
V_name_product IN products.name_product%TYPE, 
V_code_product IN products.code_product%TYPE,
V_fk_shop IN products.fk_shop%TYPE)
AS
BEGIN

    UPDATE products p
    SET p.name_product = V_name_product,
    p.code_product = V_code_product,
    p.code_product = V_fk_shop
    WHERE p.id_product = V_id_product;

END;