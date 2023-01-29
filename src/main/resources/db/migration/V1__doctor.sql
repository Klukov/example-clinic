CREATE TABLE doctor (
    id bigserial NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    rating numeric(19, 2) NULL,
    specialization varchar(63) NOT NULL,
    CONSTRAINT doctor_pkey PRIMARY KEY (id)
);
