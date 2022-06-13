package org.klukov.example.clinic.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.Patient;

@Value
@Jacksonized
@Builder
public class PatientDto {
    String firstName;
    String lastName;
    String peselNumber;

    public Patient toDomain() {
        return Patient.builder()
                .firstName(firstName)
                .lastName(lastName)
                .peselNumber(peselNumber)
                .build();
    }
}
