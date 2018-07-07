insert into distributor values (1, 'Sai Dairy');

insert into distributor_area values (1, 'Sukhsagar nagar', 1);
insert into distributor_area values (2, 'Bibwewadi', 1);
insert into distributor_area values (3, 'Katraj', 1);

insert into distributor_area_manager values (1, 'Rahul', 1);
insert into distributor_area_manager values (2, 'Keshaw', 2);
insert into distributor_area_manager values (3, 'Mahesh', 3);


insert into product_brand values (1, 'Chitale Bandhu Mithaiwale');

insert into product (id, name, unit_of_measure, selling_price, purchase_price, product_brand_id)
values
(1, 'Mhashiche Dudh','liter',51.00, 49.90, 1)
, (2, 'Gaiche Dudh','liter', 32.50, 30.00, 1)
, (3, 'Tup ', 'kg', 170.0, 180.00, 1);

insert into product_quantity_price
(id, purchase_price, selling_price, quantity, product_id)
values (1, 10, 12, 0.250, 1),
(2, 40.20, 42.20, 1, 2),
(3, 140, 150, 0.250, 3);



insert into product_brand values(2, 'Katraj Dairy');

insert into product (id, name, unit_of_measure, selling_price, purchase_price, product_brand_id)
values
(4, 'Mhashiche Dudh','liter',50.00, 48.90, 2)
, (5, 'Gaiche Dudh','liter', 42.50, 40.00, 2)
, (6, 'Tup ', 'kg', 150.0, 170.00, 2);

insert into shopkeeper values (1,'Hanuman Shopee','Katraj');
insert into shopkeeper values (2,'Ashapura Super Market','Sukhsagar');
insert into shopkeeper values (3,'Amit Vharaities','Katraj');

