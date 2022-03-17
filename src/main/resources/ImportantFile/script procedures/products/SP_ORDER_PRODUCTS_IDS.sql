create or replace PROCEDURE sp_orderproductsids (
 v_id products.id_product%TYPE)

 AS
    v_product_min_id products.id_product%TYPE;
    v_actual_value products.id_product%TYPE;
    v_products_index_check products.id_product%TYPE;
    index1 NUMBER := 1;
    index2 NUMBER := 2;
BEGIN

    IF ( v_id = 1 ) THEN

        SELECT COUNT(*)  INTO v_products_index_check FROM products;

        WHILE (index1 < v_products_index_check) LOOP
            SELECT MIN(id_product) INTO v_product_min_id FROM products WHERE id_product > index1;
            index1 := index1 + 1;
            UPDATE products SET id_product = index1 WHERE id_product = v_product_min_id;
        END LOOP;

    ELSIF ( v_id > 1 ) THEN

        SELECT MIN(id_product) INTO v_product_min_id FROM products;

        IF ( v_product_min_id = 1 ) THEN -- mettere il primo a uno e poi gli altri a + 1

            SELECT MIN(id_product) INTO v_actual_value FROM products WHERE id_product > v_product_min_id; -- seleziona il più piccolo rispetto al prmo
            UPDATE products SET id_product = v_product_min_id + 1 WHERE id_product = v_actual_value;-- qui lo setta a 2
            SELECT COUNT(*)  INTO v_products_index_check FROM products WHERE id_product > 2;--seleziono le righe prendendo in considerazione solo i record > di id 2

            WHILE (index1 <= v_products_index_check) LOOP -- loop per ogni riga eseguire l'update sull'id minore ma maggiore dell'index che incrementa
                SELECT MIN(id_product) INTO v_product_min_id FROM products WHERE id_product > index2;
                index1 := index1 +1;
                index2 := index2 + 1;
                UPDATE products SET id_product = index2 WHERE id_product = v_product_min_id; --update vera e prorpia
            END LOOP;

        ELSIF ( v_product_min_id > 1 ) THEN --medesima logica ma settando il primo record con id a 1 e il secondo a 2 e poi a cascata

           UPDATE products SET id_product = 1 WHERE id_product = v_product_min_id; -- setto il primo a 1
           SELECT MIN(id_product) INTO v_actual_value FROM products WHERE id_product > 1; -- seleziona il più piccolo rispetto al prmo
           UPDATE products SET id_product = 2  WHERE id_product = v_actual_value;-- qui lo setta a 2

           SELECT COUNT(*)  INTO v_products_index_check FROM products WHERE id_product > 2;

           WHILE (index1 <= v_products_index_check) LOOP
               SELECT MIN(id_product) INTO v_product_min_id FROM products WHERE id_product > index2;
               index1 := index1 +1;
               index2 := index2 + 1;
               UPDATE products SET id_product = index2 WHERE id_product = v_product_min_id;
           END LOOP;

       END IF;
    END IF;
END;