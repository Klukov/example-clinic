CREATE TABLE Doctor (
    id bigserial NOT NULL,
    first_name varchar(255) NULL,
    last_name varchar(255) NULL,
    rating numeric(19, 2) NULL,
    specialization int4 NULL,
    CONSTRAINT doctor_pkey PRIMARY KEY (id)
);
