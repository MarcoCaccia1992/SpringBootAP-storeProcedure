create or replace PROCEDURE sp_orderShopsIDS(
v_id shops.id_shop%TYPE)
AS
    v_shop shops%ROWTYPE;
    v_shop_MAX_ID shops.id_shop%TYPE;

    v_join country_shop_join_mtm%ROWTYPE;
    v_join_MAX_ID country_shop_join_mtm.id_shop%TYPE;

    v_product products%ROWTYPE;
BEGIN
    INSERT INTO shops (id_shop,name_shop,region_code) VALUES (v_id,'name_temp','region_code_temp'); --inserisco la riga temporanea dell'ide che cancello
    SELECT NVL(MAX(s.id_shop), 0) AS MAX_VAL INTO v_shop_MAX_ID FROM shops s; -- seleziono l'ultimo id e memorizzo i dati dentro una variabile
    SELECT * INTO v_shop FROM shops WHERE id_shop = v_shop_MAX_ID; -- inserisco i dati dell'ultimo record in una variabile
    UPDATE shops SET name_shop = v_shop.name_shop, region_code = v_shop.region_code WHERE id_shop = v_id; -- faccio l'update del record temporaneo che ho inserito

        BEGIN -- come se fossero parentesi graffe che racchiudono snippet di codice, per gestire codice annidato
            SELECT * INTO v_join FROM country_shop_join_mtm WHERE id_shop = v_shop_MAX_ID; -- inserisco i dati del record pari all'ultimo id dello shop
                EXCEPTION-- cerco nella tabella di join se c'è il dato e gestisco l'eccezione che nel caso ritorni vuoto o null
                WHEN NO_DATA_FOUND THEN
                   v_join := NULL;
        END;

    IF (v_join.id_country IS NOT NULL) THEN -- se non torna null memorizza il tutto nella tabella di destinazione e fa l'update
        UPDATE country_shop_join_mtm SET id_shop = v_id WHERE id_shop = v_shop_MAX_ID; -- faccio l'update del record mettendo l'id corretto nella tabella di join per evitare errori di constraint
    END IF;

         BEGIN -- come se fossero parentesi graffe che racchiudono snippet di codice, per gestire codice annidato
                FOR c IN (SELECT * INTO v_product FROM products WHERE fk_shop = v_shop_MAX_ID)-- inserisco i dati del record pari all'ultimo id dello shop uguale a fk_shop del products
            LOOP -- loop creato per ovviare le numerevoli righe che potrebbero venire fuori
                v_product := c; -- inserisco tutto nel loopLable ovvero "c"
                IF (c.fk_shop IS NOT NULL) THEN -- se non torna null memorizza il tutto nella tabella di destinazione e fa l'update
                    UPDATE products SET fk_shop = v_id WHERE fk_shop = v_shop_MAX_ID; -- faccio l'update del record mettendo l'id corretto nella tabella di products per evitare cancellazioni inutili in cascade
                END IF; -- e se non lo trova null applico la logica tale per cui va a modificare la product.fk_shop con la shops.id_shop corretta
            END LOOP;
                    EXCEPTION-- cerco nella tabella di join se c'è il dato e gestisco l'eccezione che nel caso ritorni vuoto o null
                    WHEN OTHERS THEN
                       v_product := NULL;
         END;



    DELETE FROM shops WHERE id_shop = v_shop_MAX_ID; -- cancello l'ultimo record per l'ordinamento degli stessi nella tabella shop
END;






