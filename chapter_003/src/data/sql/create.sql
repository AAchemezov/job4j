 CREATE DATABASE item_system;

 CREATE TABLE rules(
 	id serial PRIMARY KEY,
 	rule_name VARCHAR(255) NOT NULL
 );

 CREATE TABLE roles(
 	id serial PRIMARY KEY,
 	role_name VARCHAR(255) NOT NULL
 );

 CREATE TABLE role_rule(
 	id serial PRIMARY KEY,
 	role_id INTEGER REFERENCES roles(id) NOT NULL,
 	rule_id INTEGER REFERENCES rules(id) NOT NULL
 );

 CREATE TABLE users(
 	id serial PRIMARY KEY,
 	user_name CHARACTER VARYING (2000) NOT NULL,
 	login CHARACTER VARYING (2000) NOT NULL UNIQUE,
 	password CHARACTER VARYING (2000) NOT NULL,
 	role_id INTEGER REFERENCES roles(id)
 );

 CREATE TABLE category(
  	id serial PRIMARY KEY,
  	category_name VARCHAR(255) NOT NULL
 );

 CREATE TABLE state(
  	id serial PRIMARY KEY,
  	state_name VARCHAR(255) NOT NULL
 );

 CREATE TABLE item(
  	id serial PRIMARY KEY,
 	description TEXT,
 	category_id INTEGER REFERENCES category(id),
 	state_id INTEGER REFERENCES state(id) NOT NULL,
 	user_id INTEGER REFERENCES users(id) NOT NULL,
    create_data timestamp NOT NULL
 );

 CREATE TABLE comment(
  	id serial PRIMARY KEY,
  	comment_text TEXT,
 	item_id INTEGER REFERENCES item(id) NOT NULL
 );

 CREATE TABLE attach(
  	id serial PRIMARY KEY,
 	file_path CHARACTER VARYING (4000),
  	item_id INTEGER REFERENCES item(id) NOT NULL
 );

INSERT INTO rules(id, rule_name) values (0, 'CREATE');
INSERT INTO rules(id, rule_name) values (1, 'MODIFY');
INSERT INTO rules(id, rule_name) values (2, 'DELETE');
INSERT INTO rules(id, rule_name) values (3, 'READ');

INSERT INTO roles(id, role_name) values (0, 'ADMIN');
INSERT INTO roles(id, role_name) values (1, 'USER');

INSERT INTO role_rule(role_id, rule_id) values (0, 0);
INSERT INTO role_rule(role_id, rule_id) values (0, 1);
INSERT INTO role_rule(role_id, rule_id) values (0, 2);
INSERT INTO role_rule(role_id, rule_id) values (0, 3);

INSERT INTO role_rule(role_id, rule_id) values (1, 0);
INSERT INTO role_rule(role_id, rule_id) values (1, 3);

INSERT INTO category(id, category_name) values (0, 'BUY');
INSERT INTO category(id, category_name) values (1, 'SELL');
INSERT INTO category(id, category_name) values (2, 'BARTER');
INSERT INTO category(id, category_name) values (3, 'SUPPLY');
INSERT INTO category(id, category_name) values (4, 'RETURN');
INSERT INTO category(id, category_name) values (5, 'ADMINISTRATION');

INSERT INTO state(id, state_name) values (0, 'NEW');
INSERT INTO state(id, state_name) values (1, 'IN_WORK');
INSERT INTO state(id, state_name) values (2, 'CLOSE');

INSERT INTO users(id, user_name, login, password, role_id) values (0, 'Administrator','admin','root', 0);
INSERT INTO users(id, user_name, login, password, role_id) values (1, 'User1','user1','pass', 1);
INSERT INTO users(id, user_name, login, password, role_id) values (2, 'User2','user2','pass', 1);

INSERT INTO item(id, description, category_id, state_id, user_id, create_data)
values (0, 'Проверить работоспособность базы данных', 5, 0, 0, '2020-02-23 12:15');
INSERT INTO item(id, description, category_id, state_id, user_id, create_data)
values (1, 'Доставить товар из пункта А в пункт Б', 3, 0, 1,'2020-02-23 18:04');

INSERT INTO comment(comment_text, item_id) values ('Проверить создание заявки и изменение состояния', 0);
INSERT INTO comment(comment_text, item_id) values ('Адрес пункт А: Страна, г. Город, ул. Улица, д. 1', 1);
INSERT INTO comment(comment_text, item_id) values ('Адрес пункт B: Страна, г. Город, ул. Другая, д. 2', 1);
INSERT INTO attach(file_path, item_id) values ('//base/dostavka/tovar/smeta.xls', 1);
