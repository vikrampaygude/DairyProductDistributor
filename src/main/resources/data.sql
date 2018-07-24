insert into distributor values (1, 'Sidharth Milk Distributor');

insert into distributor_area values (1, 'Sukhsagar nagar', 1);
insert into distributor_area values (2, 'Bibwewadi', 1);
insert into distributor_area values (3, 'Katraj', 1);

insert into distributor_area_manager values (1, 'Rahul', 1);
insert into distributor_area_manager values (2, 'Keshaw', 2);
insert into distributor_area_manager values (3, 'Mahesh', 3);


insert into product_brand (id, name, short_name)
values (1, 'Chitale Bandhu Mithaiwale', '(C)'),
(2, 'Katraj Dairy', '(k)');

insert into product (id, name, short_name, unit_of_measure, selling_price, purchase_price, product_brand_id)
values
(1, 'Mhashiche Dudh','md','liter',51.00, 49.90, 1)
, (2, 'Gaiche Dudh','gd','liter', 32.50, 30.00, 1)
, (3, 'Tup ', 'tp','kg', 170.0, 180.00, 1);

insert into product_weight_price
(id, purchase_price, selling_price, weight, product_id)
values (1, 10, 12, 0.250, 1),
(2, 40.20, 42.20, 1, 2),
(3, 140, 150, 0.250, 3);


insert into product (id, name, unit_of_measure, selling_price, purchase_price, product_brand_id)
values
(4, 'Mhashiche Dudh','liter',50.00, 48.90, 2)
, (5, 'Gaiche Dudh','liter', 42.50, 40.00, 2)
, (6, 'Tup ', 'kg', 150.0, 170.00, 2);

insert into shopkeeper (id, name, address, distributor_area_id)
values (1,'Hanuman Shopee','Katraj', 1),
(2,'Balaji Super Market','Katraj', 1),
(3,'Ashapura Super Market','Sukhsagar', 2),
(4,'Amit Vharaities','Katraj', 3);

insert into DISTRIBUTOR_AREA_PRODUCT (PRODUCT_ID, DISTRIBUTOR_AREA_ID  )
values (1,1),
(2,1),
(3,1),
(4,2),
(5,2),
(6,2);


INSERT INTO AUTHORITY VALUES(1,'ROLE_ADMIN'),(2,'ROLE_USER');


INSERT INTO USERS(ID, EMAIL, ENABLED, FIRST_NAME, LAST_NAME, LAST_PASSWORD_RESET_DATE, PASSWORD, PHONE_NUMBER, USERNAME)
values
(1,'s@s.com',TRUE,'Sidharth','Hande','2018-07-20 18:17:59.487','$2a$10$VngQHgVQCbLvNYacDdvr3uukXQY4nUdPkTYcIKjbG1sY1PX7Tnmma',null,'admin'),
(2,'u@u.com',TRUE,'Sradha','H','2018-07-20 18:17:59.487','$2a$10$Pl6V9ssdS7yKmo2W4qJ1rOfLfg3q8ewIc/5m2YcsP9gM8V9teH0pG',null,'user'),
(3,'sys@sys.com',TRUE,'Prathibha','H','2018-07-20 18:17:59.487','$2a$10$H94KgybNlC1TA7LqRWkxBeaaUi7/PmvkmeGky/aHO7.tG.YLKjDLi',null,'system');

-- For now all are admin users
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID)
VALUES( 1,1),
( 2,1),
( 3,1);
