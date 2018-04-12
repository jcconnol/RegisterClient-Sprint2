CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "employee";

CREATE TABLE employee (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  employeeid character varying(32) NOT NULL DEFAULT(''),
  firstname character varying(128) NOT NULL DEFAULT(''),
  lastname character varying(128) NOT NULL DEFAULT(''),
  password character varying(512) NOT NULL DEFAULT(''),
  active boolean NOT NULL DEFAULT(FALSE), 
  classification int NOT NULL DEFAULT(0),
  managerid uuid NOT NULL,
  createdon timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT employee_pkey PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

CREATE INDEX IF NOT EXISTS ix_employee_employeeid
  ON employee
  USING btree(employeeid);

INSERT INTO employee (employeeid, firstname, lastname, password, managerid)
VALUES (100, 'test', 'test', 'test', uuid_generate_v4()),
	   (101, 'Ashley', 'Cain', 'ashleycain', uuid_generate_v4()), 
	   (102, 'Daninthia', 'Fox', 'daninthiafox', uuid_generate_v4()),
	   (103, 'John', 'Connolly', 'johnconnolly', uuid_generate_v4()),
	   (104, 'Mae', 'Larrea', 'maelarrea', uuid_generate_v4()),
	   (105, 'Marissa', 'Montes', 'marissamontes', uuid_generate_v4());
	   
