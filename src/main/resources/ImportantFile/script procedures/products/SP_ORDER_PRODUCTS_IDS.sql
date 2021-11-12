create or replace PROCEDURE sp_orderProductsIDS(
v_id products.id_product%TYPE)
AS
BEGIN
    UPDATE products SET id_product = id_product-1 WHERE id_product > v_id;
END;