insert into distributor values (1, 'Sai Dairy');

insert into distributor_area values (1, 'Sukhsagar nagar', 1);
insert into distributor_area values (2, 'Bibwewadi', 1);
insert into distributor_area values (3, 'Katraj', 1);

insert into distributor_area_manager values (1, 'Rahul', 1);
insert into distributor_area_manager values (2, 'Keshaw', 2);
insert into distributor_area_manager values (3, 'Mahesh', 3);


insert into product_brand values (1, 'Chitale Bandhu Mithaiwale');

insert into product values (1, 'Mhashiche Dudh', 1);
insert into product values (2, 'Gaiche Dudh', 1 );
insert into product values (3, 'Tup ', 1 );


insert into product_quantity_price
(id, purchase_price, selling_price, quantity, unit_of_measure, product_id)
values (1, 10, 12, 0.250,'liter', 1),
(2, 40.20, 42.20, 1,'liter', 2),
(3, 140, 150, 0.250,'Kg', 3);



insert into product_brand values(2, 'Katraj Dairy');

insert into product values (4, 'Mhashiche Dudh', 2);
insert into product values (5, 'Gaiche Dudh', 2);
insert into product values (6, 'Tup ', 2);

insert into shopkeeper values (1,'Hanuman Shopee','Katraj');
insert into shopkeeper values (2,'Ashapura Super Market','Sukhsagar');
insert into shopkeeper values (3,'Amit Vharaities','Katraj');

