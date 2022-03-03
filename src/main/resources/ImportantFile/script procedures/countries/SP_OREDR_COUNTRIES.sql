create or replace PROCEDURE sp_orderCountriesIDS(
v_id countries.id_country%TYPE)
AS
     v_country countries%ROWTYPE;
     v_country_MAX_ID countries.id_country%TYPE;

     v_join country_shop_join_mtm%ROWTYPE;
     v_join_MAX_ID country_shop_join_mtm.id_shop%TYPE;
BEGIN
    INSERT INTO countries (id_country,name_country,acronym_shop) VALUES (v_id,'name_temp','acronym_shop_temp'); --inserisco la riga temporanea dell'ide che cancello
    SELECT NVL(MAX(c.id_country), 0) AS MAX_VAL INTO v_country_MAX_ID FROM countries c; -- seleziono l'ultimo id e memorizzo i dati dentro una variabile
    SELECT * INTO v_country FROM countries WHERE id_country = v_country_MAX_ID; -- inserisco i dati dell'ultimo record in una variabile
    UPDATE countries SET name_country = v_country.name_country, acronym_shop = v_country.acronym_shop WHERE id_country = v_id; -- faccio l'update del record temporaneo che ho inserito

    SELECT * INTO v_join FROM country_shop_join_mtm WHERE id_country = v_country_MAX_ID; -- inserisco i dati del record pari all'ultimo id dello shop
    UPDATE country_shop_join_mtm SET id_shop = v_id WHERE id_country = v_country_MAX_ID; -- faccio l'update del record mettendo l'id corretto nella tabella di join per evitare errori di constraint

    DELETE FROM countries WHERE id_country = v_country_MAX_ID; -- cancello l'ultimo record per l'ordinamento degli stessi
END;