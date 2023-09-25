--Procedure para adicionar uma Scooter
CREATE OR REPLACE PROCEDURE addScooter(phar_email VARCHAR, capacity NUMBER, power NUMBER, maxPower NUMBER, battery NUMBER, weight NUMBER)
AS
BEGIN
INSERT INTO SCOOTER VALUES(seq_scooter.nextval,addScooter.phar_email,addScooter.capacity,addScooter.power,addScooter.maxPower,addScooter.battery,addScooter.weight);
END;
/

--Procedure para adicionar um Park
CREATE OR REPLACE PROCEDURE addPark(phar_email VARCHAR, designation VARCHAR, spotsCapacity INT, powerCapacity NUMBER)
AS
BEGIN
    INSERT INTO PARK VALUES(seq_park.nextval,addPark.phar_email,addPark.designation,addPark.spotsCapacity,addPark.powerCapacity);
END;
/

--Procedure para adicionar um Spot
CREATE OR REPLACE PROCEDURE addSpot(id INT, chargingSpot VARCHAR, id_park INT)
AS
BEGIN
    INSERT INTO SPOT VALUES(addSpot.id,id_park,addSpot.chargingSpot,NULL,NULL);
END;
/


--Procedure para remover uma Scooter
CREATE OR REPLACE PROCEDURE removeScooter(ID INT)
IS
BEGIN
DELETE FROM SCOOTER WHERE SCOOTER.ID = removeScooter.ID;
END;
/

--Procedure para remover um Park
CREATE OR REPLACE PROCEDURE removePark(ID INT)
    IS
BEGIN
    DELETE FROM PARK WHERE PARK.ID = removePark.ID;
END;
/

--Procedure para remover um Spot
CREATE OR REPLACE PROCEDURE removeSpot(ID INT, ID_PARK INT)
    IS
BEGIN
    DELETE FROM SPOT WHERE SPOT.ID = removeSpot.ID AND SPOT.ID_PARK = removeSpot.ID_PARK;
END;
/

--Procedure para adicionar um Drone
CREATE OR REPLACE PROCEDURE addDrone(phar_email VARCHAR, capacity NUMBER, power NUMBER, maxPower NUMBER, battery NUMBER,weight NUMBER)
AS
BEGIN
INSERT INTO DRONE VALUES(seq_drone.nextval,addDrone.phar_email,addDrone.capacity,addDrone.power,addDrone.maxPower,addDrone.battery,addDrone.weight);
END;
/

--Procedure para remover uma Drone
CREATE OR REPLACE PROCEDURE removeDrone(ID INT)
IS
BEGIN
DELETE FROM DRONE WHERE DRONE.ID = removeDrone.ID;
END;
/

--Procedure para remover um Address
CREATE OR REPLACE PROCEDURE removeAddress(gpsCoordinates VARCHAR)
    IS
BEGIN
    DELETE FROM ADDRESS WHERE ADDRESS.GPSCOORDINATES = removeAddress.gpsCoordinates;
END;
/

--Procedure para dar update a uma Scooter
CREATE OR REPLACE PROCEDURE updateScooter(ID INT, capacity NUMBER, power NUMBER, maxPower NUMBER,weight NUMBER)
    IS
BEGIN
    UPDATE SCOOTER
    SET
        CAPACITY = updateScooter.capacity,
        POWER = updateScooter.power,
        maxpower = updateScooter.maxPower,
        WEIGHT = updateScooter.weight
    WHERE
        SCOOTER.id = updateScooter.ID;
END;
/

--Procedure para dar update a uma Drone
CREATE OR REPLACE PROCEDURE updateDrone(ID INT, capacity NUMBER, power NUMBER, maxPower NUMBER,weight NUMBER)
    IS
BEGIN
    UPDATE DRONE
    SET
        CAPACITY = updateDrone.capacity,
        POWER = updateDrone.power,
        maxpower = updateDrone.maxPower,
        WEIGHT = updateDrone.weight
    WHERE
        DRONE.id = updateDrone.ID;
END;
/

CREATE OR REPLACE PROCEDURE removePharmacy(email VARCHAR)
IS
BEGIN
DELETE FROM Pharmacy WHERE Pharmacy.email = removePharmacy.email;
END;
/

--Procedure para adicionar um Product
CREATE OR REPLACE PROCEDURE addProduct(reference NUMBER, name VARCHAR, description VARCHAR, price NUMBER, weight NUMBER)
AS
BEGIN
INSERT INTO PRODUCT VALUES(addProduct.reference,addProduct.name,addProduct.description,addProduct.price,addProduct.weight);
END;
/

--Procedure para remover um Product
CREATE OR REPLACE PROCEDURE removeProduct(REFERENCE INT)
IS
BEGIN
DELETE FROM PRODUCT WHERE PRODUCT.REFERENCE = removeProduct.REFERENCE;
END;
/

CREATE OR REPLACE PROCEDURE updateProduct(reference INT, name VARCHAR, description VARCHAR, price NUMBER, weight NUMBER)
    IS
BEGIN
    UPDATE PRODUCT
    SET
        PRODUCT.name = updateProduct.name,
        PRODUCT.description = updateProduct.description,
        PRODUCT.price = updateProduct.price,
        PRODUCT.weight = updateProduct.weight
    WHERE
        PRODUCT.reference = updateProduct.reference;
END;
/

--Procedure para adicionar uma Scooter
CREATE OR REPLACE PROCEDURE addAddress(GPSCoordinates VARCHAR, street VARCHAR, postalCode VARCHAR, doorNumber INT, locality VARCHAR, elevation INT)
AS
BEGIN
    INSERT INTO ADDRESS VALUES(addAddress.GPSCoordinates, addAddress.street, addAddress.postalCode, addAddress.doorNumber, addAddress.locality, addAddress.elevation);
END;
/

--Procedure para adicionar uma Pharmacy
CREATE OR REPLACE PROCEDURE addPharmacy(email VARCHAR, coordinates VARCHAR, designation VARCHAR)
AS
BEGIN
    INSERT INTO PHARMACY VALUES(addPharmacy.email, addPharmacy.coordinates, addPharmacy.designation);
END;
/

--Procedure para adicionar um cliente
CREATE OR REPLACE PROCEDURE addClient(email VARCHAR,gps_coordinates VARCHAR,credit_card_number NUMBER,validity_date DATE,ccv NUMBER,credits NUMBER)
AS
BEGIN
    INSERT INTO CLIENTT VALUES(addClient.email,addClient.gps_coordinates,addClient.credit_card_number,addClient.validity_date,addClient.ccv,addClient.credits);
END;
/

--Procedure para remover um Client
CREATE OR REPLACE PROCEDURE removeClient(email VARCHAR)
    IS
BEGIN
    DELETE FROM CLIENTT WHERE CLIENTT.EMAIL = removeClient.EMAIL;
END;
/

--Procedure para adicionar um Courier
CREATE OR REPLACE PROCEDURE addCourier(phar_email VARCHAR, nif NUMBER, email VARCHAR, phoneNumber NUMBER, socialSecurityNumber NUMBER, status VARCHAR)
AS
BEGIN
    INSERT INTO Courier VALUES(addCourier.nif, addCourier.phar_email, addCourier.email, addCourier.phoneNumber, addCourier.socialSecurityNumber, addCourier.status);
END;
/

--Procedure para adicionar um User
CREATE OR REPLACE PROCEDURE addUser( name VARCHAR, email VARCHAR,pwd VARCHAR)
AS
BEGIN
    INSERT into USERR VALUES (addUser.name, addUser.email, addUser.pwd);
END;
/

--
CREATE OR REPLACE PROCEDURE updateCourier(statusC VARCHAR,emailC VARCHAR)
    IS
BEGIN
UPDATE COURIER
SET

       status=updatecourier.statusC
WHERE
        COURIER.email = updatecourier.emailC;
END;
/

--Procedure to remove Courier
CREATE OR REPLACE PROCEDURE removeCourier(EMAIL VARCHAR)
IS
BEGIN
DELETE FROM Courier WHERE COURIER.EMAIL = removeCourier.EMAIL;
END;
/

--Procedure to update Deliveries
CREATE OR REPLACE PROCEDURE updateDeliveryByScooter(id NUMBER,status VARCHAR)
AS
BEGIN
  	UPDATE DELIVERY_SCOOTER
SET
    DELIVERY_SCOOTER.STATUS=updateDeliveryByScooter.STATUS
WHERE
        DELIVERY_SCOOTER.DELIVERY_SCOOTERID=updateDeliveryByScooter.ID;
END;
/
CREATE OR REPLACE PROCEDURE addDeliveryByScooter(status varchar ,email_courier varchar,id_scooter number,idDelivery number)
AS
BEGIN
INSERT INTO DELIVERY_scooter VALUES(seq_delivery_scooter.nextval,addDeliveryByScooter.status,addDeliveryByScooter.email_courier,addDeliveryByScooter.idDelivery,addDeliveryByScooter.id_scooter);
END;
/
CREATE OR REPLACE PROCEDURE addDelivery
AS
BEGIN
INSERT INTO DELIVERY VALUES(seq_delivery.nextval);
END;
/

--Procedure to add an Order
CREATE OR REPLACE PROCEDURE addOrder(finalPrice number,clientEmail varchar,pharmacyEmail varchar)
AS
BEGIN
INSERT INTO ORDERR VALUES(seq_orderr.nextval,addOrder.finalPrice,NULL,addOrder.clientEmail,addOrder.pharmacyEmail);
END;
/

--Procedure to add a PRODUCT_ORDER
CREATE OR REPLACE PROCEDURE addProductOrder(idOrder int ,reference int ,amount int)
AS
BEGIN
INSERT INTO PRODUCT_ORDER VALUES(addProductOrder.amount,addProductOrder.reference,addProductOrder.idOrder);
END;
/

CREATE OR REPLACE PROCEDURE updateOrder(id NUMBER,deliveryId Number)
AS
BEGIN
    UPDATE orderr
    SET
        orderr.DELIVERY_ID=updateOrder.deliveryid
    WHERE
            orderr.IDORDER=updateOrder.ID;
END;
/
--Procedure to remove User
CREATE OR REPLACE PROCEDURE removeUser(EMAIL VARCHAR)
    IS
BEGIN
    DELETE FROM userr WHERE userr.EMAIL = removeUser.EMAIL;
END;
/

--Procedure to remove Order
CREATE OR REPLACE PROCEDURE removeOrder(IDORDER INT)
    IS
BEGIN
    DELETE FROM orderr WHERE orderr.IDORDER = removeOrder.IDORDER;
END;
/

CREATE OR REPLACE PROCEDURE removeProductOrder(referencee int,idOrder int)
    IS
BEGIN
    DELETE from Product_Order WHERE product_order.reference = removeProductOrder.referencee AND product_order.idOrder = removeProductOrder.idOrder;
END;
/

CREATE OR REPLACE PROCEDURE removeDelivery(id int)
is 
begin
delete from delivery where delivery.deliveryid=removeDelivery.id;
end;
/

CREATE OR REPLACE PROCEDURE updateSpot(id NUMBER,idPark Number,idScooter NUMBER)
AS
BEGIN
    UPDATE SPOT
    SET
        SPOT.ID_SCOOTER=updateSpot.idScooter
    WHERE
        spot.ID=updateSpot.id and spot.ID_PARK=updateSpot.idpark;
END;
/
CREATE OR REPLACE PROCEDURE updateSpot2(id NUMBER,idPark Number)
AS
BEGIN
    UPDATE SPOT
    SET
        SPOT.ID_SCOOTER = null,
        SPOT.ID_DRONE = null
    WHERE
        spot.ID=updateSpot2.id and spot.ID_PARK=updateSpot2.idpark;
END;
/
CREATE OR REPLACE PROCEDURE updateSpot3(id NUMBER,idPark Number,idDrone NUMBER)
AS
BEGIN
    UPDATE SPOT
    SET
        SPOT.ID_DRONE=updateSpot3.idDrone
    WHERE
        spot.ID=updateSpot3.id and spot.ID_PARK=updateSpot3.idpark;
END;
/
CREATE OR REPLACE PROCEDURE updateScooterBattery(battery NUMBER,idScooter Number)
AS
BEGIN
    UPDATE SCOOTER
    SET
        SCOOTER.BATTERY = updateScooterBattery.battery
    WHERE
        scooter.id=updateScooterBattery.idScooter;
END;
/

CREATE OR REPLACE PROCEDURE addDeliveryByDrone(status varchar,id_drone number,idDelivery number)
AS
BEGIN
    INSERT INTO DELIVERY_DRONE VALUES(seq_delivery_drone.nextval,addDeliveryByDrone.status,addDeliveryByDrone.idDelivery,addDeliveryByDrone.id_drone);
END;
/
CREATE OR REPLACE PROCEDURE updateDeliveryByDrone(id NUMBER,status VARCHAR)
AS
BEGIN
    UPDATE DELIVERY_drone
    SET
        DELIVERY_drone.STATUS=updateDeliveryByDrone.STATUS
    WHERE
            DELIVERY_drone.DELIVERY_droneID=updateDeliveryByDrone.ID;
END;
/

CREATE OR REPLACE PROCEDURE removeDeliveryByScooter(ID INT,email VARCHAR)
IS
BEGIN
DELETE FROM DELIVERY_SCOOTER WHERE DELIVERY_SCOOTER.ID_SCOOTER = removeDeliveryByScooter.ID AND DELIVERY_SCOOTER.EMAIL_COURIER= removeDeliveryByScooter.email;
END;
/

CREATE OR REPLACE PROCEDURE removeDeliveryByDrone(ID INT)
IS
BEGIN
DELETE FROM DELIVERY_Drone WHERE DELIVERY_Drone.ID_DRONE = removeDeliveryByDRONE.ID;
END;
/

--Procedure para adicionar um PharmacyProduct
CREATE OR REPLACE PROCEDURE addPharmacyProduct(reference INTEGER, pharEmail VARCHAR, amount INTEGER)
AS
BEGIN
INSERT INTO PHARMACYPRODUCT VALUES(addPharmacyProduct.reference,addPharmacyProduct.pharEmail,addPharmacyProduct.amount);
END;
/

--Procedure para remover um PharmacyProduct
CREATE OR REPLACE PROCEDURE removePharmacyProduct(REFERENCE INT, PHAREMAIL VARCHAR)
IS
BEGIN
DELETE FROM PHARMACYPRODUCT WHERE PHARMACYPRODUCT.REFERENCE = removePharmacyProduct.REFERENCE AND removePharmacyProduct.PHAREMAIL=PHARMACYPRODUCT.PHAR_EMAIL;
END;
/

CREATE OR REPLACE PROCEDURE updatePharmacyProduct(reference INT, pharemail VARCHAR, amount INTEGER)
    IS
BEGIN
    UPDATE PHARMACYPRODUCT
    SET
        PHARMACYPRODUCT.amount = updatePharmacyProduct.amount
    WHERE
        PHARMACYPRODUCT.reference = updatePharmacyProduct.reference
    AND
        PHARMACYPRODUCT.PHAR_EMAIL = updatePharmacyProduct.pharemail;
END;
/

CREATE OR REPLACE PROCEDURE addTransference(pharEmail1 VARCHAR, pharEmail2 VARCHAR, reference INTEGER, amount INTEGER)
AS
BEGIN
INSERT INTO TRANSFERENCE VALUES(seq_transference.nextval,addTransference.pharEmail1,addTransference.pharEmail2,addTransference.reference,addTransference.amount);
END;
/

CREATE OR REPLACE PROCEDURE removeTransference(id INTEGER)
AS
BEGIN
    DELETE FROM TRANSFERENCE WHERE TRANSFERENCE.id = removeTransference.id;
END;
/

--Procedure para dar update a uma Scooter
CREATE OR REPLACE PROCEDURE updateClient(Email varchar,creds INTEGER)
    IS
BEGIN
    UPDATE CLIENTT
    SET
        CREDITS = creds
    WHERE
            CLIENTT.Email = updateClient.Email;
END;
/
--Procedure para dar update a uma Drone
CREATE OR REPLACE PROCEDURE updateDrone2(ID INT, battery INT)
    IS
BEGIN
    UPDATE DRONE
    SET
       BATTERY = updateDrone2.BATTERY
    WHERE
        DRONE.id = updateDrone2.ID;
END;
/

commit;