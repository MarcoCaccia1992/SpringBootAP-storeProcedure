create or replace FUNCTION maxId_products_function
RETURN products.id_product%TYPE
AS
    v_id products.id_product%TYPE;
BEGIN 
    SELECT NVL(MAX(p.id_product), 0) AS MAX_VAL INTO v_id
    FROM products p;
    RETURN v_id;

END;