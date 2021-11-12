create or replace PROCEDURE sp_deleteCountries(
v_id countries.id_country%TYPE)
AS
BEGIN
    DELETE FROM countries WHERE v_id = countries.id_country;
END;