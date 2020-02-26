CREATE TABLE company
 (
id integer NOT NULL,
name character varying,
CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
id integer NOT NULL,
name character varying,
company_id integer,
CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company	(id	,name			)
			values	(1	,'Google'		),
					(2	,'goo'			),
					(3	,'Samsung'		),
					(4	,'Apple'		),
					(5	,'Yandex'		);

insert into person	(id	,name			,company_id	)
			values	(1	,'Andew'		,1			),
					(2	,'John'			,1			),
					(3	,'Micael'		,3			),
					(4	,'Anna'			,5			),
					(5	,'Eva'			,7			),
					(6	,'Sara'			,5			),
					(7	,'Nicolas'		,5			),
					(8	,'Mike'			,null		),
					(9	,'Nicolas'		,0			),
					(10	,'Mikel'		,1			);

--1) Retrieve in a single query:
-- - names of all persons that are NOT in the company with id = 5
-- - company name for each person
select  p.name as name,
        c.name as company
from
    person p
    left join company c on p.company_id = c.id
where
    p.company_id is null
    or p.company_id <> 5;

-- 2) Select the name of the company with the maximum number of persons + number of persons in this company
select * from (
	select 	c.name 		as company_name,
			count(p.id) as number_persons
	from
		person p
		right join company c on p.company_id = c.id
	GROUP by c.id) com_num
ORDER BY com_num.number_persons DESC limit 1;
