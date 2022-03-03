create or replace PROCEDURE sp_orderShopsIDS(
v_id shops.id_shop%TYPE)
AS
    v_shop shops%ROWTYPE;
    v_shop_MAX_ID shops.id_shop%TYPE;

    v_join country_shop_join_mtm%ROWTYPE;
    v_join_MAX_ID country_shop_join_mtm.id_shop%TYPE;
BEGIN
    INSERT INTO shops (id_shop,name_shop,region_code) VALUES (v_id,'name_temp','region_code_temp'); --inserisco la riga temporanea dell'ide che cancello
    SELECT NVL(MAX(s.id_shop), 0) AS MAX_VAL INTO v_shop_MAX_ID FROM shops s; -- seleziono l'ultimo id e memorizzo i dati dentro una variabile
    SELECT * INTO v_shop FROM shops WHERE id_shop = v_shop_MAX_ID; -- inserisco i dati dell'ultimo record in una variabile
    UPDATE shops SET name_shop = v_shop.name_shop, region_code = v_shop.region_code WHERE id_shop = v_id; -- faccio l'update del record temporaneo che ho inserito

    SELECT * INTO v_join FROM country_shop_join_mtm WHERE id_shop = v_shop_MAX_ID; -- inserisco i dati del record pari all'ultimo id dello shop
    UPDATE country_shop_join_mtm SET id_shop = v_id WHERE id_shop = v_shop_MAX_ID; -- faccio l'update del record mettendo l'id corretto nella tabella di join per evitare errori di constraint

    DELETE FROM shops WHERE id_shop = v_shop_MAX_ID; -- cancello l'ultimo record per l'ordinamento degli stessi nella tabella shop
END;