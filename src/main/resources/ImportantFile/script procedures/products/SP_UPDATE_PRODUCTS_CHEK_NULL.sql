create or replace PROCEDURE sp_updateProductsCheckNull(
V_id_product IN products.id_product%TYPE,
V_name_product IN products.name_product%TYPE, 
V_code_product IN products.code_product%type,
V_fk_shop IN products.fk_shop%type)
AS
BEGIN

    IF(V_name_product IS NULL) THEN

        UPDATE products p
        SET p.code_product = V_code_product
        WHERE p.id_product = V_id_product;

    ELSIF(V_code_product IS NULL) THEN

        UPDATE products p
        SET p.name_product = V_name_product
        WHERE p.id_product = V_id_product;
    
    ELSIF(V_fk_shop IS NULL AND V_code_product IS NULL) THEN

        UPDATE products p
        SET p.name_product = V_name_product
        WHERE p.id_product = V_id_product;
        
     ELSIF(V_fk_shop IS NULL AND V_name_product IS NULL) THEN

        UPDATE products p
        SET p.code_product = V_code_product
        WHERE p.id_product = V_id_product;    
    
    ELSE

        UPDATE products p
        SET p.name_product = V_name_product, p.code_product = V_code_product, p.fk_shop = V_fk_shop
        WHERE p.id_product = V_id_product;
    END IF;

EXCEPTION
WHEN OTHERS THEN
    null;

END;