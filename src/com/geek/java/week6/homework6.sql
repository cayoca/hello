CREATE DATABASE IF NOT EXISTS EC;

USE EC;

CREATE TABLE IF NOT EXISTS t_user
(
	id INT(11) primary key,
	name VARCHAR(25),
	password VARCHAR(25),
	nick_name VARCHAR(25),
	id_card INT(16)
);

CREATE TABLE IF NOT EXISTS t_suplier
(
	id INT(11) primary key,
	name VARCHAR(25),
	registration_id VARCHAR(25),
	person VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS t_goods
(
	id INT(11) primary key,
	code VARCHAR(25),
	name VARCHAR(25),
	catagory VARCHAR(25),
	weight INT(11),
	suplier_id INT(11),
	foreign key (suplier_id)REFERENCES t_suplier(id)
);

CREATE TABLE IF NOT EXISTS t_order
(
	id INT(11) primary key,
	user_id INT(11),
	amount FLOAT,
	status INT(2),
	foreign key (user_id)REFERENCES t_user(id)
);

CREATE TABLE IF NOT EXISTS t_order_detail
(
	id INT(11) primary key,
	order_id INT(11),
	goods_id INT(11),
	price FLOAT,
	foreign key (order_id)REFERENCES t_order(id),
	foreign key (goods_id)REFERENCES t_goods(id)
);

CREATE TABLE IF NOT EXISTS t_shopping_cart
(
	id INT(11) primary key,
	user_id INT(11),
	amount FLOAT,
	special_price FLOAT,
	foreign key (user_id)REFERENCES t_user(id)
);

CREATE TABLE IF NOT EXISTS t_shopping_cart_detail
(
	id INT(11) primary key,
	shopping_cart_id INT(11),
	goods_id INT(11),
	count INT(5),
	price FLOAT,	
	special_price FLOAT,
	foreign key (shopping_cart_id)REFERENCES t_shopping_cart(id),
	foreign key (goods_id)REFERENCES t_goods(id)
);