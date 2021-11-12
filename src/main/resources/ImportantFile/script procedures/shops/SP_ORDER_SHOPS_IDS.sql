create or replace PROCEDURE sp_orderShopsIDS(
v_id shops.id_shop%TYPE)
AS
BEGIN
    UPDATE shops SET id_shop = id_shop-1 WHERE id_shop > v_id;
END;