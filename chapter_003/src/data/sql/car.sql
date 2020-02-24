---------------------------------------------------заполнение базы---------------------------------------------
CREATE TABLE car_body(
    id VARCHAR(255) PRIMARY KEY,
    model VARCHAR(512) NOT NULL
);

CREATE TABLE transmission(
    id VARCHAR(255) PRIMARY KEY,
    model VARCHAR(512) NOT NULL
);

CREATE TABLE engine(
    id VARCHAR(255) PRIMARY KEY,
    model VARCHAR(512) NOT NULL
);

CREATE TABLE car(
    id VARCHAR(255) PRIMARY KEY,
    model VARCHAR(512) NOT NULL,
    car_body_id VARCHAR(255) REFERENCES car_body(id) NOT NULL,
    transmission_id VARCHAR(255) REFERENCES transmission(id) NOT NULL,
    engine_id VARCHAR(255) REFERENCES engine(id) NOT NULL
);

insert into car_body (id, model) values ('cb00001', 'Кузов, toyota camry, model: tc_098765');
insert into car_body (id, model) values ('cb00002', 'Кузов, toyota camry, model: tc_023424');
insert into car_body (id, model) values ('cb00003', 'Кузов, bmw x5, model: bmw_x5_65');
insert into car_body (id, model) values ('cb00004', 'Кузов, bmw x3, model: bmw x3_24');

insert into transmission (id, model) values ('tr00001', 'Коробка передач, toyota camry, model: tc_098765');
insert into transmission (id, model) values ('tr00002', 'Коробка передач, toyota camry, model: tc_023424');
insert into transmission (id, model) values ('tr00003', 'Коробка передач, bmw x5, model: bmw_x5_65');
insert into transmission (id, model) values ('tr00004', 'Коробка передач, bmw x3, model: bmw x3_24');

insert into engine (id, model) values ('e00001', 'Двигатель, toyota camry, model: tc_098765');
insert into engine (id, model) values ('e00002', 'Двигатель, toyota camry, model: tc_023424');
insert into engine (id, model) values ('e00003', 'Двигатель, bmw x5, model: bmw_x5_65');
insert into engine (id, model) values ('e00004', 'Двигатель, bmw x3, model: bmw x3_24');

insert into car (id, model, car_body_id, transmission_id, engine_id)
values ('toyota_camry_00002', 'toyota camry, model: tc_023424', 'cb00002', 'tr00002', 'e00002');
insert into car (id, model, car_body_id, transmission_id, engine_id)
values ('bmw_x5_00002', 'bmw x5, model: bmw_x5_65', 'cb00003', 'tr00003', 'e00003');
--------------------------------------------------------------------------------------------------------

---------------------------------------------------Запросы ---------------------------------------------
--1. Вывести список всех машин и все привязанные к ним детали.
select  c.id        as id,
        c.model     as model,
        tr.model    as transmission,
        e.model     as engine,
        cb.model    as car_body
from car c
    left join transmission tr on c.transmission_id = tr.id
    left join engine e on c.engine_id = e.id
    left join car_body cb on c.car_body_id = cb.id;

--2. Вывести отдельно детали, которые не используются в машине, кузова, двигатели, коробки передач.
select tr.*
from transmission tr
    left join car c on tr.id = c.transmission_id
where c.id is null;

select e.*
from engine e
    left join car c on e.id = c.engine_id
where c.id is null;

select cb.*
from car_body cb
    left join car c on cb.id = c.car_body_id
where c.id is null;