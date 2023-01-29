CREATE TABLE visit (
    id bigserial NOT NULL,
    status varchar(31) NOT NULL,
    time_from timestamp NOT NULL,
    time_to timestamp NOT NULL,
    doctor_id int8 NOT NULL,
    patient_id int8 NULL,
    patient_remarks varchar(1023) NULL,
    CONSTRAINT visit_pkey PRIMARY KEY (id)
);
