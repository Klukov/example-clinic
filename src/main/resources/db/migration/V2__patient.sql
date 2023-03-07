CREATE
    TABLE
        patient(
            id bigserial NOT NULL,
            first_name VARCHAR(255) NOT NULL,
            last_name VARCHAR(255) NOT NULL,
            pesel_number VARCHAR(11) NOT NULL,
            CONSTRAINT patient_pkey PRIMARY KEY(id)
        );
