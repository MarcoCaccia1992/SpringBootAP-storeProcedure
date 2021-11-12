create or replace PROCEDURE sp_updateCountries(
V_id_country IN countries.id_country%TYPE,
V_name_country IN countries.name_country%TYPE,
V_acronym_shop IN countries.acronym_shop%TYPE)
AS
BEGIN

    UPDATE countries c
    SET c.name_country = V_name_country, c.acronym_shop = V_acronym_shop
    WHERE c.id_country = V_id_country;

END;