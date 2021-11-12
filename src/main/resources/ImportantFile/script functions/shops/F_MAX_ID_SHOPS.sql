create or replace FUNCTION maxId_shops_function
RETURN shops.id_shop%TYPE
AS
    v_id shops.id_shop%TYPE;
BEGIN 
    SELECT NVL(MAX(s.id_shop), 0) AS MAX_VAL INTO v_id
    FROM shops s;
    RETURN v_id;

END;