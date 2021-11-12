create or replace PROCEDURE sp_deleteProducts(
v_id products.id_product%TYPE)
AS
BEGIN
    DELETE FROM products WHERE v_id = products.id_product;
END;