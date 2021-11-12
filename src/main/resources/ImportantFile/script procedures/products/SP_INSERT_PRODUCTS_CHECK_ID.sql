create or replace PROCEDURE sp_insertProductsCheckId(
v_name_product products.name_product%TYPE,
v_code_product products.code_product%TYPE,
v_fk_shop products.fk_shop%TYPE)
AS
BEGIN
    INSERT INTO products VALUES((maxId_products_function + 1), 
    v_name_product,
    v_code_product,
    v_fk_shop);
END;