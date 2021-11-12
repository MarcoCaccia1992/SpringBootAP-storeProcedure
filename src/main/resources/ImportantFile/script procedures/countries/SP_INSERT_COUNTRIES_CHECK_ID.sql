create or replace PROCEDURE sp_insertCountriesCheckId(
v_name_country countries.name_country%TYPE,
v_acronym_shop countries.acronym_shop%TYPE)
AS
BEGIN
    INSERT INTO countries VALUES((maxId_countries_function + 1), 
    v_name_country, v_acronym_shop);
END;