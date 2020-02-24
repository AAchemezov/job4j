---------------------------------------------------заполнение базы---------------------------------------------
 create table product(
 	id serial PRIMARY KEY,
 	name VARCHAR(255),
 	type_id INTEGER REFERENCES type(id) NOT NULL,
 	expired_date TIMESTAMP,
 	price DOUBLE precision
 );

 create table type(
 	id serial PRIMARY KEY,
 	name VARCHAR(255)
 );

 insert into type (id, name) values(0,'СЫР');
 insert into type (id, name) values(1,'МОРОЖЕННОЕ');
 insert into type (id, name) values(2,'ПИРОЖЕННОЕ');
 insert into type (id, name) values(3,'МОЛОКО');
 insert into type (id, name) values(4,'ХЛЕБ');
 insert into type (id, name) values(5,'РЫБА');
 insert into type (id, name) values(6,'МЯСО');
 insert into type (id, name) values(7,'НИ РЫБА НИ МЯСО');
 insert into product(name, type_id, expired_date, price) values('Голандский',0,'28-08-2020', 340.50);
 insert into product(name, type_id, expired_date, price) values('Рокфор',0,'28-08-2020', 220.50);
 insert into product(name, type_id, expired_date, price) values('мороженое "Морозко"',1,'28-08-2020', 34);
 insert into product(name, type_id, expired_date, price) values('Мороженое мясо',6,'27-08-2020', 240.50);
 insert into product(name, type_id, expired_date, price) values('мясо мороженое',6,'27-08-2020', 245.50);
 insert into product(name, type_id, expired_date, price) values('Семга мороженая',5,'28-12-2020', 3000.50);
 insert into product(name, type_id, expired_date, price) values('Молоко "БМП" 2.5%',0,'1-03-2020', 30.50);
 insert into product(name, type_id, expired_date, price) values('Молоко "БМП" 3.2%',0,'2-03-2020', 37.50);
 insert into product(name, type_id, expired_date, price) values('Молоко "БМП" 3.5%',0,'3-03-2020', 41.80);
 insert into product(name, type_id, expired_date, price) values('Молоко "БМП" 4.0%',0,'15-03-2020', 55.20);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Хлеб 01',4,'25-02-2020', 15);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Пироженка 02',2,'25-02-2020', 21);
 insert into product(name, type_id, expired_date, price) values('Сельдь',5,'25-02-2021', 100.50);
 insert into product(name, type_id, expired_date, price) values('Сельдь 10',5,'25-02-2021', 110);
 insert into product(name, type_id, expired_date, price) values('Сельдь 12',5,'25-02-2021', 172);
 insert into product(name, type_id, expired_date, price) values('Сельдь 456',5,'25-02-2021', 122);
 insert into product(name, type_id, expired_date, price) values('Сельдь 33',5,'25-02-2021', 111);
--------------------------------------------------------------------------------------------------------

---------------------------------------------------Запросы ---------------------------------------------
-- 1. Написать запрос получение всех продуктов с типом "СЫР"
select p.* from product p join type t on p.type_id =t.id and t.name = 'СЫР';

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
select * from product where name like '%мороженое%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
select * from product where EXTRACT(MONTH FROM expired_date) = EXTRACT(MONTH FROM now()) + 1;

-- 4. Написать запрос, который выводит самый дорогой продукт.
select * from product ORDER BY price desc LIMIT 1;

-- 5. Написать запрос, который выводит количество всех продуктов определенного типа.
select t.*, count(p.id) as count_products from type t join product p on p.type_id = t.id GROUP BY t.id;

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
select p.* from product p join type t on p.type_id = t.id and t.name in ('СЫР','МОЛОКО');

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
select t.* from type t where (select count(p.id) from product p where p.type_id = t.id) < 10;
-- или
select * from
            (select t.*, count(p.id) as count_products
               from type t join product p on p.type_id = t.id
            GROUP BY t.id) e
        WHERE e.count_products < 10;

-- 8. Вывести все продукты и их тип
select t.name, p.* from product p join type t on p.type_id = t.id;
