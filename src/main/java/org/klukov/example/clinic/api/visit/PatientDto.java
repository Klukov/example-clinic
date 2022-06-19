package org.klukov.example.clinic.api.visit;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.visit.Patient;

@Value
@Jacksonized
@Builder
class PatientDto {
    String firstName;
    String lastName;
    String peselNumber;

    Patient toDomain() {
        return Patient.builder()
                .firstName(firstName)
                .lastName(lastName)
                .peselNumber(peselNumber)
                .build();
    }
}
