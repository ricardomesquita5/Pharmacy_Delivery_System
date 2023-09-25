CREATE OR REPLACE TRIGGER trgchangecourieremailindeliverytonull BEFORE
    DELETE ON courier
    FOR EACH ROW
BEGIN
    UPDATE delivery_scooter
    SET
        email_courier = NULL
    WHERE
        delivery_scooter.email_courier = :old.email;

END;
/

CREATE OR REPLACE TRIGGER trgchangescooteridindeliverytonull BEFORE
    DELETE ON scooter
    FOR EACH ROW
BEGIN
    UPDATE delivery_scooter
    SET
        id_scooter = NULL
    WHERE
        delivery_scooter.id_scooter = :old.id;

END;
/

CREATE OR REPLACE TRIGGER trgchangedroneidindeliverytonull BEFORE
    DELETE ON drone
    FOR EACH ROW
BEGIN
    UPDATE delivery_drone
    SET
        id_drone = NULL
    WHERE
        delivery_drone.id_drone = :old.id;

END;
/

COMMIT;