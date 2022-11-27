package org.klukov.example.clinic.api.patient;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Jacksonized
@Builder
class PatientDto {

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @Size(min = 11, max = 11)
    String peselNumber;
}
