create or replace PROCEDURE sp_orderCountrisIDS(
v_id countries.id_country%TYPE)
AS
BEGIN
    UPDATE countries SET id_country = id_country-1 WHERE id_country > v_id;
END;