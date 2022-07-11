package org.klukov.example.clinic.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.Patient;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Jacksonized
@Builder
public class PatientDto {

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @Size(min = 11, max = 11)
    String peselNumber;

    public Patient toDomain() {
        return Patient.builder()
                .firstName(firstName)
                .lastName(lastName)
                .peselNumber(peselNumber)
                .build();
    }
}
