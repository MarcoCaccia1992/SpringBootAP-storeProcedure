create or replace PROCEDURE sp_deleteCountries(
v_id countries.id_country%TYPE)
AS
    v_country countries.name_country%TYPE;
    v_country_check countries.name_country%TYPE;
BEGIN
    DELETE FROM country_shop_join_mtm WHERE v_id = country_shop_join_mtm.id_country;

    SELECT name_country INTO v_country_check FROM countries WHERE id_country = v_id;

        IF (v_country_check = 'Lombardia')
        THEN v_country := 'lo';
        ELSIF (v_country_check = 'Puglia')
        THEN v_country := 'pu';
        ELSIF (v_country_check = 'Calabria')
        THEN v_country := 'ca';
        ELSIF (v_country_check = 'Veneto')
        THEN v_country := 've';
        END IF;

    DELETE FROM shops WHERE region_code = v_country;
    DELETE FROM countries WHERE v_id = countries.id_country;
END;