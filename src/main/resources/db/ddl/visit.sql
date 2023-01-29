CREATE TABLE public.visit (
    id bigserial NOT NULL,
    doctor_id int8 NULL,
    patient_id int8 NULL,
    patient_remarks varchar(1023) NULL,
    status int4 NULL,
    time_from timestamp NULL,
    time_to timestamp NULL,
    CONSTRAINT visit_pkey PRIMARY KEY (id)
);
