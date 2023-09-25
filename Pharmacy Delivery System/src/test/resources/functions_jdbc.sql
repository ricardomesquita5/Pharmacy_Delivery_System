-- Funcao retorna a referencia de um objeto cursor
CREATE OR REPLACE FUNCTION getScooter(ID INT)
    RETURN SYS_REFCURSOR
AS
    curScooter SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooter
    OPEN curScooter FOR SELECT * FROM SCOOTER WHERE SCOOTER.ID = getScooter.ID; RETURN curScooter; END;
/

CREATE OR REPLACE FUNCTION getLastPark(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curPark SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooter
    OPEN curPark FOR SELECT * FROM PARK WHERE PARK.PHAR_EMAIL = getLastPark.email ORDER BY ID DESC FETCH FIRST 1 ROW ONLY;
    RETURN curPark; END;
/


CREATE OR REPLACE FUNCTION getDrone(ID INT)
    RETURN SYS_REFCURSOR
AS
    curDrone SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curDrone
    OPEN curDrone FOR SELECT * FROM DRONE WHERE DRONE.ID = getDrone.ID; RETURN curDrone; END;
/

-- Funcao retorna a referencia de um objeto cursor
CREATE OR REPLACE FUNCTION getClient(email varchar)
    RETURN SYS_REFCURSOR
AS
    curClient SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curClient
    OPEN curClient FOR SELECT * FROM CLIENTT WHERE CLIENTT.email = getClient.email; RETURN curClient; END;
/

-- Funcao retorna a referencia de um objeto cursor
CREATE OR REPLACE FUNCTION getProduct(ID INT)
    RETURN SYS_REFCURSOR
AS
    curProduct SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a refer�nncia desse objeto na variavel de cursor curProduct
    OPEN curProduct FOR SELECT * FROM PRODUCT WHERE PRODUCT.REFERENCE = getProduct.ID; RETURN curProduct; END;
/

CREATE OR REPLACE FUNCTION getAddress(coordinates varchar)
    RETURN SYS_REFCURSOR
AS
    curAddress SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curSailor
    OPEN curAddress FOR SELECT * FROM Address WHERE Address.GPSCoordinates = getAddress.coordinates; RETURN curAddress; END;
/

CREATE OR REPLACE FUNCTION getPharmacy(email varchar)
    RETURN SYS_REFCURSOR
AS
    curPharmacy SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curSailor
    OPEN curPharmacy FOR SELECT * FROM Pharmacy WHERE Pharmacy.email = getPharmacy.email; RETURN curPharmacy; END;
/

CREATE OR REPLACE FUNCTION getCourier(email varchar)
    RETURN SYS_REFCURSOR
AS
    curCourier SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN

    OPEN curCourier FOR SELECT * FROM Courier WHERE Courier.email = getCourier.email; RETURN curCourier; END;
/

-- Funcao retorna a quantidade de scooters
CREATE OR REPLACE FUNCTION getAmountOfScooters(pharEmail varchar)
    RETURN INT
AS
    amount INT;	-- variavel de cursor do tipo predefinido INT
BEGIN
    -- conta a quantidade de scooters e armazena na variavel
    SELECT COUNT(*) INTO amount FROM SCOOTER WHERE SCOOTER.PHAR_EMAIL = getAmountOfScooters.pharEmail; RETURN amount; END;
/

-- Funcao retorna os parques de uma farmacia
CREATE OR REPLACE FUNCTION getAllParks(pharEmail varchar)
    RETURN SYS_REFCURSOR
AS
    curParks SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooters
    OPEN curParks FOR SELECT * FROM PARK WHERE PARK.phar_email = getAllParks.pharEmail; RETURN curParks; END;
/

-- Funcao retorna os lugares de um parque
CREATE OR REPLACE FUNCTION getAllSpots(id_park INT)
    RETURN SYS_REFCURSOR
AS
    curSpots SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooters
    OPEN curSpots FOR SELECT * FROM SPOT WHERE SPOT.id_park = getAllSpots.id_park; RETURN curSpots; END;
/

-- Funcao retorna a scooters de uma farmacia
CREATE OR REPLACE FUNCTION getAllScooters(pharEmail varchar)
    RETURN SYS_REFCURSOR
AS
    curScooters SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooters
    OPEN curScooters FOR SELECT * FROM SCOOTER WHERE SCOOTER.phar_email = getAllScooters.pharEmail ORDER BY SCOOTER.ID DESC; RETURN curScooters; END;
/

-- Funcao retorna a quantidade de drones
CREATE OR REPLACE FUNCTION getAmountOfDrones(pharEmail varchar)
    RETURN INT
AS
    amount INT;	-- variavel de cursor do tipo predefinido INT
BEGIN
    -- conta a quantidade de Drones e armazena na variavel
    SELECT COUNT(*) INTO amount FROM DRONE WHERE DRONE.PHAR_EMAIL = getAmountOfDrones.pharEmail; RETURN amount; END;
/

-- Funcao retorna a Drones de uma farmacia
CREATE OR REPLACE FUNCTION getAllDrones(pharEmail varchar)
    RETURN SYS_REFCURSOR
AS
    curDrones SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curDrones
    OPEN curDrones FOR SELECT * FROM DRONE WHERE DRONE.phar_email = getAllDrones.pharEmail; RETURN curDrones; END;
/

CREATE OR REPLACE FUNCTION getAllProducts
    RETURN SYS_REFCURSOR
AS
    curProducts SYS_REFCURSOR;    -- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curSailor
    OPEN curProducts FOR SELECT * FROM PRODUCT; RETURN curProducts; END;
/
--

-- Funcao retorna um user
CREATE OR REPLACE FUNCTION findUser(userEmail VARCHAR, userPassword VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curUser SYS_REFCURSOR;
BEGIN
    OPEN curUser FOR SELECT * FROM USERR WHERE USERR.EMAIL = findUser.userEmail AND USERR.PASSWORD = findUser.userPassword; RETURN curUser;END;
/

-- Funcao retorna um admin
CREATE OR REPLACE FUNCTION isAdmin(userEmail VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curAdmin SYS_REFCURSOR;
BEGIN
    OPEN curAdmin FOR SELECT * FROM ADMINN WHERE ADMINN.EMAIL = isAdmin.userEmail; RETURN curAdmin;END;
/

-- Funcao retorna um cliente
CREATE OR REPLACE FUNCTION isClient(userEmail VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curClient SYS_REFCURSOR;
BEGIN
    OPEN curClient FOR SELECT * FROM CLIENTT WHERE CLIENTT.EMAIL = isClient.userEmail; RETURN curClient ;END;
/

-- Funcao retorna um courier
CREATE OR REPLACE FUNCTION isCourier(userEmail VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curCourier SYS_REFCURSOR;
BEGIN
    OPEN curCourier FOR SELECT * FROM COURIER WHERE COURIER.EMAIL = isCourier.userEmail; RETURN curCourier;END;
/

-- Funcao retorna um user
CREATE OR REPLACE FUNCTION getUser(userEmail VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curUser SYS_REFCURSOR;
BEGIN
    OPEN curUser FOR SELECT * FROM USERR WHERE USERR.EMAIL=getUser.userEmail;Return curUser;END;
/

CREATE OR REPLACE FUNCTION getDelivery_Scooter(status VARCHAR, emailCourier VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curDelivery SYS_REFCURSOR;
BEGIN
    OPEN curDelivery FOR SELECT * FROM DELIVERY_SCOOTER WHERE DELIVERY_SCOOTER.EMAIL_COURIER=getDelivery_scooter.emailCourier AND UPPER(DELIVERY_SCOOTER.STATUS)=UPPER(getDelivery_scooter.STATUS);RETURN CURDELIVERY;END;
/
CREATE OR REPLACE FUNCTION getDelivery_Drone(status VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curDelivery SYS_REFCURSOR;
BEGIN
    OPEN curDelivery FOR SELECT * FROM DELIVERY_drone WHERE  DELIVERY_drone.STATUS=getDelivery_drone.STATUS;RETURN CURDELIVERY;END;
/


CREATE OR REPLACE FUNCTION getAllOrders(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curOrder SYS_REFCURSOR;
BEGIN
    OPEN curOrder FOR SELECT * FROM ORDERR WHERE ORDERR.PHARMACY_EMAIL=getAllOrders.email and ORDERR.DELIVERY_ID is null ;RETURN curOrder;END;
/

CREATE OR REPLACE FUNCTION getEmailPharmacyByEmailCourier(email VARCHAR)
    RETURN VARCHAR
AS
    v_emailPharmacy VARCHAR2(200 byte);
BEGIN
    SELECT COURIER.PHAR_EMAIL INTO v_emailPharmacy FROM COURIER WHERE COURIER.EMAIL=getEmailPharmacyByEmailCourier.email;RETURN v_emailPharmacy;END;
/

CREATE OR REPLACE FUNCTION getOrderAddressCoordinatesByClientEmail (email VARCHAR)
    RETURN varchar
AS
    v_coordinates VARCHAR2(200 byte);
BEGIN
    SELECT ADDRESS.gpscoordinates INTO v_coordinates FROM CLIENTT
                                                              INNER JOIN ADDRESS ON clientt.GPSCOORDINATES=address.GPSCOORDINATES
    where clientt.email=getOrderAddressCoordinatesByClientEmail.email;
    RETURN v_coordinates;END;
/
CREATE OR REPLACE FUNCTION getPharmacyAddressCoordinatesByCourierEmail (email VARCHAR)
    RETURN varchar
AS
    v_coordinates VARCHAR2(200 byte);
BEGIN
    SELECT ADDRESS.GPSCOORDINATES INTO v_coordinates FROM courier INNER JOIN pharmacy ON
            pharmacy.email=courier.phar_email
                                                                  INNER JOIN ADDRESS ON pharmacy.GPSCOORDINATES=address.GPSCOORDINATES
    where courier.email=getPharmacyAddressCoordinatesByCourierEmail.email;
    RETURN v_coordinates;END;
/

CREATE OR REPLACE FUNCTION getEmailClientbyOrderId (id number)
    RETURN varchar
AS
    v_email VARCHAR2(200 byte);
BEGIN
    SELECT orderr.client_email INTO v_email FROM orderr where orderr.idorder=id;

    RETURN v_email;END;
/


CREATE OR REPLACE FUNCTION getAvailableScooters(pharEmail varchar)
    RETURN SYS_REFCURSOR
AS
    curScooters SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooters
    OPEN curScooters FOR SELECT * from scooter inner join
                                       spot on spot.ID_SCOOTER=scooter.id
                                               inner join park on park.id=spot.id_park where SCOOTER.phar_email=getAvailableScooters.pharEmail and UPPER(park.designation) Like 'SCOOTER';
    RETURN curScooters; END;
/

CREATE OR REPLACE FUNCTION getAvailableDrones(pharEmail varchar)
    RETURN SYS_REFCURSOR
AS
    curDrones SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curDrones
    OPEN curDrones FOR SELECT * from drone inner join
                                     spot on spot.ID_DRONE=DRONE.id
                                           inner join park on park.id=spot.id_park where DRONE.phar_email=getAvailableDrones.pharEmail and UPPER(park.designation) Like 'DRONE';
    RETURN curDrones; END;
/

CREATE OR REPLACE FUNCTION getweightByOrderId(id number)
    RETURN number
AS
    v_weight number;
BEGIN
    SELECT SUM(PRODUCT_ORDER.AMOUNT * PRODUCT.WEIGHT) INTO v_weight FROM PRODUCT INNER JOIN PRODUCT_ORDER
                                                                                            ON PRODUCT_ORDER.REFERENCE = PRODUCT.REFERENCE WHERE PRODUCT_ORDER.IDORDER=getweightByOrderId.ID;
    RETURN v_weight;END;
/
CREATE OR REPLACE FUNCTION getAllCouriersOfPharmacy(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curCourier SYS_REFCURSOR;
BEGIN
    OPEN curCourier FOR SELECT * FROM COURIER WHERE COURIER.PHAR_EMAIL = getAllCouriersOfPharmacy.email; RETURN curCourier;END;
/
CREATE OR REPLACE FUNCTION getAlldeliveriesWithScooter(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curDeliveries SYS_REFCURSOR;
BEGIN
    OPEN curDeliveries FOR SELECT * FROM Delivery_Scooter WHERE delivery_scooter.email_courier = getAllDeliveriesWithScooter.email; RETURN curDeliveries;END;
/
CREATE OR REPLACE FUNCTION getScooterByemailCourierOnDel(email VARCHAR)
    RETURN SYS_REFCURSOR
AS curScooter SYS_REFCURSOR;
BEGIN OPEN curScooter FOR SELECT * FROM SCOOTER INNER JOIN DELIVERY_scooter ON SCOOTER.ID=DELIVERY_scooter.ID_SCOOTER
                          WHERE DELIVERY_scooter.EMAIL_COURIER=getScooterByemailCourierOnDel.email and UPPER (delivery_scooter.STATUS) LIKE 'STARTED';
RETURN curScooter ;END;
/
CREATE OR REPLACE FUNCTION getAvailableSpots(email VARCHAR,designation VARCHAR)
    RETURN SYS_REFCURSOR
AS curSpots SYS_REFCURSOR;
BEGIN OPEN curSpots FOR SELECT * FROM SPOT INNER JOIN PARK ON PARK.ID=SPOT.ID_PARK
                        WHERE PARK.PHAR_EMAIL=getAvailableSpots.email and spot.ID_SCOOTER IS NULL AND UPPER(PARK.DESIGNATION)=UPPER(getAvailableSpots.DESIGNATION);
RETURN curSpots ;END;
/
CREATE OR REPLACE FUNCTION getOrdersByDeliveryId(id number)
    RETURN SYS_REFCURSOR
AS curOrders SYS_REFCURSOR;
BEGIN OPEN curOrders FOR SELECT * FROM ORDERR WHERE ORDERR.DELIVERY_ID=getOrdersByDeliveryId.ID;
RETURN curOrders ;END;
/
CREATE OR REPLACE FUNCTION getSpotByScooterId(id number,designation VARCHAR)
    RETURN SYS_REFCURSOR
AS curSpot SYS_REFCURSOR;
BEGIN OPEN curSpot FOR SELECT * FROM SPOT INNER JOIN PARK ON SPOT.ID_PARK=PARK.ID
                       WHERE SPOT.ID_SCOOTER=getSpotByScooterId.ID AND UPPER(PARK.DESIGNATION)=UPPER(getSpotByScooterId.DESIGNATION);
RETURN curSpot ;END;
/

CREATE OR REPLACE FUNCTION getSpotByDroneId(id number,designation VARCHAR)
    RETURN SYS_REFCURSOR
AS curSpot SYS_REFCURSOR;
BEGIN OPEN curSpot FOR SELECT * FROM SPOT INNER JOIN PARK ON SPOT.ID_PARK=PARK.ID
                       WHERE SPOT.ID_Drone=getSpotByDroneId.ID AND UPPER(PARK.DESIGNATION)=UPPER(getSpotByDroneId.DESIGNATION);
RETURN curSpot ;END;
/

CREATE OR REPLACE FUNCTION getSpot(id number,idpark number)
    RETURN SYS_REFCURSOR
AS curSpot SYS_REFCURSOR;
BEGIN OPEN curSpot FOR SELECT * FROM SPOT WHERE SPOT.id=getSpot.ID and spot.id_park=getSpot.idpark;
RETURN curSpot ;END;
/
CREATE OR REPLACE FUNCTION getParkScooterLimit(email VARCHAR)
    RETURN INT
AS
    limitScooters INT;
BEGIN
    SELECT SUM(PARK.SPOTSCAPACITY) INTO limitScooters FROM PARK WHERE PARK.PHAR_EMAIL = getParkScooterLimit.email AND UPPER(PARK.DESIGNATION) LIKE 'SCOOTER'; RETURN limitScooters;END;
/

CREATE OR REPLACE FUNCTION getParkDroneLimit(email VARCHAR)
    RETURN INT
AS
    limitDrones INT;
BEGIN
    SELECT SUM(PARK.SPOTSCAPACITY) INTO limitDrones FROM PARK WHERE PARK.PHAR_EMAIL = getParkDroneLimit.email AND UPPER(PARK.DESIGNATION) LIKE 'DRONE'; RETURN limitDrones;END;
/
CREATE OR REPLACE FUNCTION getDeliveryByDrone(status VARCHAR,idDrone number)
    RETURN SYS_REFCURSOR
AS
    curDelivery SYS_REFCURSOR;
BEGIN
    OPEN curDelivery FOR SELECT * FROM DELIVERY_drone WHERE delivery_drone.id_drone=getDeliveryByDrone.idDrone AND DELIVERY_drone.STATUS=getDeliveryByDrone.STATUS;RETURN CURDELIVERY;END;
/
CREATE OR REPLACE FUNCTION getAvailableCouriers(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curCourier SYS_REFCURSOR;
BEGIN
    OPEN curCourier FOR SELECT * FROM COURIER WHERE COURIER.PHAR_EMAIL=getAvailableCouriers.EMAIL AND UPPER(COURIER.STATUS) LIKE 'AVAILABLE';RETURN curCourier;END;
/

CREATE OR REPLACE FUNCTION getLastDelivery
    RETURN SYS_REFCURSOR
AS
    curDelivery SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooter
    OPEN curDelivery FOR SELECT * FROM DELIVERY  ORDER BY DELIVERYID DESC FETCH FIRST 1 ROW ONLY;
    RETURN curDelivery; END;
/

CREATE OR REPLACE FUNCTION getLastClientOrder(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curOrder SYS_REFCURSOR;	-- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooter
    OPEN curOrder FOR SELECT * FROM ORDERR WHERE ORDERR.CLIENT_EMAIL = GETLASTCLIENTORDER.email ORDER BY ORDERR.IDORDER DESC FETCH FIRST 1 ROW ONLY;
    RETURN curOrder; END;
/
CREATE OR REPLACE FUNCTION getAllDeliveriesByDrone(id number)
    RETURN SYS_REFCURSOR
AS
    curDeliveries SYS_REFCURSOR;
BEGIN
    OPEN curDeliveries FOR SELECT * FROM Delivery_drone WHERE delivery_drone.id_drone=getAllDeliveriesByDrone.id; RETURN curDeliveries;END;
/

CREATE OR REPLACE FUNCTION getPharmacyProduct(ID INT, email VARCHAR)
RETURN SYS_REFCURSOR
AS
  curPharmacyProduct SYS_REFCURSOR;    -- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
  -- criar um objeto cursor e armazenar a refer?nncia desse objeto na variavel de cursor curProduct
OPEN curPharmacyProduct FOR SELECT * FROM PHARMACYPRODUCT WHERE PHARMACYPRODUCT.REFERENCE = getPharmacyProduct.ID AND PHARMACYPRODUCT.PHAR_EMAIL = getPharmacyProduct.email; RETURN curPharmacyProduct; END;
/

CREATE OR REPLACE FUNCTION getAllPharmacyProduct
    RETURN SYS_REFCURSOR
AS
    curPharmacyProducts SYS_REFCURSOR;    -- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curSailor
    OPEN curPharmacyProducts FOR SELECT * FROM PHARMACYPRODUCT; RETURN curPharmacyProducts; END;
/

CREATE OR REPLACE FUNCTION getAllPharmacyProductEmail(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curPharmacyProducts SYS_REFCURSOR;    -- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curSailor
    OPEN curPharmacyProducts FOR SELECT * FROM PHARMACYPRODUCT WHERE PHARMACYPRODUCT.PHAR_EMAIL=getAllPharmacyProductEmail.email; RETURN curPharmacyProducts; END;
/

CREATE OR REPLACE FUNCTION getLastTransference(email VARCHAR)
    RETURN SYS_REFCURSOR
AS
    curTransferences SYS_REFCURSOR;    -- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curSailor
    OPEN curTransferences FOR SELECT * FROM TRANSFERENCE WHERE TRANSFERENCE.PHAR_EMAIL_IN=getLastTransference.email ORDER BY TRANSFERENCE.ID DESC FETCH FIRST 1 ROW ONLY; RETURN curTransferences; END;
/

CREATE OR REPLACE FUNCTION getTotalAmountOfProduct(ref INTEGER)
    RETURN INTEGER
AS
    amount INTEGER;	-- variavel de cursor do tipo predefinido INT
BEGIN
    -- conta a quantidade de scooters e armazena na variavel
    amount := 0;
    SELECT SUM(PHARMACYPRODUCT.AMOUNT) INTO amount FROM PHARMACYPRODUCT WHERE PHARMACYPRODUCT.REFERENCE = getTotalAmountOfProduct.ref; RETURN amount; END;
/

CREATE OR REPLACE FUNCTION getLastDeliveryByScooterId(id INTEGER)
    RETURN SYS_REFCURSOR
AS
    email SYS_REFCURSOR;
BEGIN
    
 OPEN email FOR SELECT * FROM DELIVERY_SCOOTER WHERE DELIVERY_SCOOTER.Id_scooter = getLastDeliveryByScooterId.id
    Order BY DELIVERY_SCOOTER.DELIVERY_SCOOTERID desc fetch first 1 row only ; RETURN email;END;
/

CREATE OR REPLACE FUNCTION getAllProductOrders
    RETURN SYS_REFCURSOR
AS
    curProductOrder SYS_REFCURSOR;    -- variavel de cursor do tipo predefinido SYS_REFCURSOR
BEGIN
    -- criar um objeto cursor e armazenar a referencia desse objeto na variavel de cursor curScooters
    OPEN curProductOrder FOR SELECT * FROM PRODUCT_ORDER ORDER BY PRODUCT_ORDER.IDORDER DESC; RETURN curProductOrder; END;
/

commit;
