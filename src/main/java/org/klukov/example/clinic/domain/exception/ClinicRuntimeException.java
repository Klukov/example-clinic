package org.klukov.example.clinic.domain.exception;

public class ClinicRuntimeException extends RuntimeException {

    public ClinicRuntimeException(String message) {
        super(message);
    }
}
