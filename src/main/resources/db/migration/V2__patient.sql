CREATE TABLE patient (
    id bigserial NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    pesel_number varchar(11) NOT NULL,
    CONSTRAINT patient_pkey PRIMARY KEY (id)
);
