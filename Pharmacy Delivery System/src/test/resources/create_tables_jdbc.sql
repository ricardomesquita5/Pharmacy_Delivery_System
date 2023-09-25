--!!!! WARNING !!!!
--!!!! Eliminar TODAS as tabelas do schema !!!!
--!!!!
begin
  for r in (select 'drop table ' || table_name || ' cascade constraints' cmd from user_tables order by table_name)
  loop
    execute immediate(r.cmd);
  end loop;
end;
/

--Create Table ADDRESS
CREATE TABLE ADDRESS
(	 GPSCOORDINATES VARCHAR(200) constraint PK_ADDRESS primary key,
     STREET VARCHAR(200),
     POSTALCODE VARCHAR(200) CONSTRAINT ckPostalCodeAddress CHECK(REGEXP_LIKE(POSTALCODE, '^\d{4}-\d{3}$')),
     DOORNUMBER INT,
     LOCALITY VARCHAR(200),
     ELEVATION INT
);

--Create Table PHARMACY
CREATE TABLE PHARMACY
(	EMAIL VARCHAR(200)  constraint PK_PHARMACY primary key
                        CONSTRAINT ck_pharmacy_email CHECK(Email LIKE '%@%.com' OR Email LIKE '%@%.pt'),
     GPSCOORDINATES VARCHAR2(200) constraint FK_PHARMACY_ADDRESS references ADDRESS(GPSCOORDINATES),
     DESIGNATION VARCHAR(200)
);

--Create Table User
CREATE TABLE USERR
(    NAME VARCHAR(200) CONSTRAINT nn_userr_name not null,
     EMAIL VARCHAR(200) CONSTRAINT pk_userr primary key
                        CONSTRAINT ck_userr_email CHECK(Email LIKE '%@%.com' OR Email LIKE '%@%.pt'),
     PASSWORD VARCHAR(200) constraint nn_userr_password not null
);

insert into userr values('adm','adm@lapr.pt','123');

--Create Table Admin
CREATE TABLE ADMINN
(    EMAIL VARCHAR(200) constraint PK_ADMIN primary key CONSTRAINT FK_ADMIN_EMAIL references USERR(EMAIL)
);

insert into adminn values ('adm@lapr.pt');

--Create Table Client
CREATE TABLE CLIENTT
(    EMAIL VARCHAR(200) constraint PK_CLIENT primary key CONSTRAINT FK_CLIENT_EMAIL references USERR(EMAIL),
     GPSCOORDINATES VARCHAR2(200) constraint FK_CLIENT_ADDRESS references ADDRESS(GPSCOORDINATES),
     CREDIT_CARD_NUMBER NUMERIC(16) CONSTRAINT ckClienteCreditCard CHECK(REGEXP_LIKE(CREDIT_CARD_NUMBER, '^\d{16}$')),
     VALIDITY_DATE DATE,
     CCV NUMERIC(9) CONSTRAINT ckClienteCCV CHECK(REGEXP_LIKE(CCV, '^\d{3}$')),
     CREDITS NUMBER CONSTRAINT ckClienteCredits CHECK(CREDITS>=0)
);

--Create Table Scooter
CREATE TABLE SCOOTER
(	ID INT constraint PK_SCOOTER primary key,
    PHAR_EMAIL VARCHAR2(200 BYTE) constraint fk_scooter_pharmacy references pharmacy(email),
     CAPACITY NUMBER CONSTRAINT ckScooterCapacity CHECK(CAPACITY>0),
     POWER NUMBER CONSTRAINT ckScooterPower CHECK(POWER>0),
     MAXPOWER NUMBER CONSTRAINT ckScooterMaxPower CHECK(MAXPOWER>0),
     BATTERY NUMBER CONSTRAINT ckScooterBattery CHECK(BATTERY>=0 AND BATTERY<=100),
     WEIGHT NUMBER CONSTRAINT ckScooterWeight CHECK(WEIGHT>0)
);

--Create Table Drone
CREATE TABLE DRONE
(	ID INT constraint PK_DRONE primary key,
    PHAR_EMAIL VARCHAR2(200 BYTE) constraint fk_drone_pharmacy references pharmacy(email),
    CAPACITY NUMBER CONSTRAINT ckDroneCapacity CHECK(CAPACITY>0),
    POWER NUMBER CONSTRAINT ckDronePower CHECK(POWER>0),
    MAXPOWER NUMBER CONSTRAINT ckDroneMaxPower CHECK(MAXPOWER>0),
    BATTERY NUMBER CONSTRAINT ckDroneBattery CHECK(BATTERY>=0 AND BATTERY<=100),
    WEIGHT NUMBER CONSTRAINT ckDroneWeight CHECK(WEIGHT>0)
);

--Create Table PRODUCT
CREATE TABLE PRODUCT
(     REFERENCE INT constraint PK_PRODUCT primary key,
     NAME VARCHAR(200),
     DESCRIPTION VARCHAR(200),
     PRICE NUMBER(30,2) CONSTRAINT ckProductPrice CHECK(PRICE>0),
     WEIGHT NUMBER(30,2) CONSTRAINT ckProductWeight CHECK(WEIGHT>0)
);

CREATE TABLE PHARMACYPRODUCT
(     REFERENCE INT constraint fk_product_pharmacy_product references product(reference),
     PHAR_EMAIL VARCHAR2(200 BYTE) constraint fk_product_pharmacy references pharmacy(email),
     AMOUNT INT CONSTRAINT ckProductAmount CHECK(AMOUNT>=0),
     CONSTRAINT PK_PHARMACY_PRODUCT primary key(REFERENCE,PHAR_EMAIL)
);

--Create Table Courier
CREATE TABLE COURIER
(	NIF NUMERIC(9)  CONSTRAINT nnCourierNif NOT NULL
                    CONSTRAINT ckCourierNif CHECK(REGEXP_LIKE(NIF, '^\d{9}$'))
                    CONSTRAINT ukCourierNif UNIQUE,
     PHAR_EMAIL VARCHAR2(200) constraint FK_COURIER_PHARMACY references PHARMACY(EMAIL),
     EMAIL VARCHAR(200) constraint PK_COURIER primary key CONSTRAINT FK_COURIER_EMAIL references USERR(EMAIL),
     PHONENUMBER  NUMERIC(9)  CONSTRAINT ckCourierPhoneNumber CHECK(REGEXP_LIKE(PHONENUMBER, '^\d{9}$')),
     SOCIALSECURITYNUMBER NUMERIC(11)  CONSTRAINT ckCourierSocialNumber CHECK(REGEXP_LIKE(SOCIALSECURITYNUMBER, '^\d{11}$')),
     STATUS VARCHAR(200) CONSTRAINT ckCourierStatus CHECK(UPPER(STATUS) LIKE 'AVAILABLE' OR UPPER(STATUS) LIKE 'UNAVAILABLE')
);

--Create Table DELIVERY
CREATE TABLE DELIVERY
( DELIVERYID INT constraint PK_DELIVERY primary key);

CREATE TABLE DELIVERY_DRONE
(DELIVERY_DRONEID INT CONSTRAINT PK_DELIVERY_DRONE primary key ,
 STATUS VARCHAR2(200 BYTE) CONSTRAINT ckdELIVERY_droneStatus CHECK(UPPER(STATUS) LIKE 'ASSIGNED' OR UPPER(STATUS) LIKE 'FINISHED'),
 ID_DELIVERY constraint FK_DELIVERY_DELIVERYDRONE references DELIVERY(DELIVERYID),
 ID_drone NUMBER constraint FK_DELIVERY_DRONE_DRONE references DRONE(ID)
);

CREATE TABLE DELIVERY_SCOOTER
(   DELIVERY_SCOOTERID INT CONSTRAINT PK_DELIVERY_SCOOTER primary key,
    STATUS VARCHAR2(200 BYTE) CONSTRAINT ckdELIVERYStatus CHECK(UPPER(STATUS) LIKE 'ASSIGNED' OR UPPER(STATUS) LIKE 'FINISHED' OR UPPER(STATUS) LIKE 'STARTED'),
    EMAIL_COURIER VARCHAR2(200 BYTE) constraint FK_DELIVERY_COURIER references COURIER(EMAIL),
    ID_DELIVERY constraint FK_DELIVERY_DELIVERYSCOOTER references DELIVERY(DELIVERYID),
    ID_SCOOTER constraint FK_DELIVERY_SCOOTER_SCOOTER references SCOOTER(ID)
);

--Create Table ORDER
Create TABLE ORDERR
(   IDORDER INT constraint PK_ORDER primary key,
    FINALPRICE NUMBER constraint ckOrderPrice CHECK(FINALPRICE>0),
    DELIVERY_ID INT constraint FK_DELIVERY_ORDERR references DELIVERY(DELIVERYID),
    CLIENT_EMAIL VARCHAR(200) constraint FK_CLIENT_ORDERR references CLIENTT(EMAIL),
    PHARMACY_EMAIL VARCHAR(200) constraint FK_ORDERR_PHARMACY references PHARMACY(EMAIL)
);

--Create Table PRODUCT_ORDER
CREATE TABLE PRODUCT_ORDER
(   AMOUNT INT constraint ckProductOrder CHECK(AMOUNT>0),
    REFERENCE INT constraint fk_product_order_reference references PRODUCT(REFERENCE),
    IDORDER INT constraint fk_product_order_idorder references ORDERR(IDORDER),
    CONSTRAINT PK_PRODUCT_ORDER primary key(REFERENCE,IDORDER)
);

--Create Table Park
CREATE TABLE PARK
(   ID INT constraint PK_PARK primary key,
    PHAR_EMAIL VARCHAR(200) constraint fk_park_pharmacy references pharmacy(email),
    DESIGNATION VARCHAR(200),
    SPOTSCAPACITY INT constraint ckParkSpotsCapacity CHECK(SPOTSCAPACITY>0),
    POWERCAPACITY NUMBER constraint ckParkPowerCapacity CHECK(POWERCAPACITY>0)
);

--Create Table SPOT
CREATE TABLE SPOT
(   ID INT,
    ID_PARK INT constraint fk_spot_park references park(id),
    CHARGINGSPOT VARCHAR(200),
    ID_SCOOTER INT constraint FK_SPOT_SCOOTER references SCOOTER(ID),
    ID_DRONE INT constraint FK_SPOT_DRONE references DRONE(ID),
    CONSTRAINT PK_SPOT primary key(ID,ID_PARK)
);

CREATE TABLE TRANSFERENCE
(   ID INT constraint PK_TRANSFERENCE primary key,
    PHAR_EMAIL_IN VARCHAR(200) constraint fk_transference_in references pharmacy(email),
    PHAR_EMAIL_OUT VARCHAR(200) constraint fk_transference_out references pharmacy(email),
    REFERENCE INT constraint fk_transference_reference references PRODUCT(REFERENCE),
    AMOUNT INT constraint ckTransferenceAmount CHECK(AMOUNT>0)
);

--sequences
--select * from user_sequences;
declare
  v_cmd varchar(2000);
begin
  for r in (select sequence_name from user_sequences)
  loop
    v_cmd := 'drop sequence ' || r.sequence_name;
    execute immediate(v_cmd);
  end loop;
  --
  for r in (select table_name from user_tables)
  loop
    v_cmd := 'create sequence seq_' || r.table_name;
    execute immediate(v_cmd);
  end loop;
end;
/
--
commit;