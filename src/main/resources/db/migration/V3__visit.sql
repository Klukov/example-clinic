CREATE
    TABLE
        visit(
            id bigserial NOT NULL,
            status VARCHAR(31) NOT NULL,
            time_from TIMESTAMP NOT NULL,
            time_to TIMESTAMP NOT NULL,
            doctor_id int8 NOT NULL,
            patient_id int8 NULL,
            patient_remarks VARCHAR(1023) NULL,
            CONSTRAINT visit_pkey PRIMARY KEY(id)
        );
