CREATE
    TABLE
        doctor(
            id bigserial NOT NULL,
            first_name VARCHAR(255) NOT NULL,
            last_name VARCHAR(255) NOT NULL,
            rating NUMERIC(
                19,
                2
            ) NULL,
            specialization VARCHAR(63) NOT NULL,
            CONSTRAINT doctor_pkey PRIMARY KEY(id)
        );
