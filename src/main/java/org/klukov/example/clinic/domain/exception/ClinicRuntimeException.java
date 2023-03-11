package org.klukov.example.clinic.domain.exception;

@SuppressWarnings({"PMD.MissingSerialVersionUID"}) //this object is not deserialized it does not need versionUUID
public class ClinicRuntimeException extends RuntimeException {

    public ClinicRuntimeException(String message) {
        super(message);
    }
}
