create or replace FUNCTION maxId_countries_function
RETURN countries.id_country%TYPE
AS
    v_id countries.id_country%TYPE;
BEGIN 
    SELECT NVL(MAX(c.id_country), 0) AS MAX_VAL INTO v_id
    FROM countries c;
    RETURN v_id;

END;