CREATE TABLE public.patient (
    id bigserial NOT NULL,
    first_name varchar(255) NULL,
    last_name varchar(255) NULL,
    pesel_number varchar(255) NULL,
    CONSTRAINT patient_pkey PRIMARY KEY (id)
);
