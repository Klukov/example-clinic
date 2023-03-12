package org.klukov.example.clinic.api.patient;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
class PatientDto {

    @NotEmpty String firstName;

    @NotEmpty String lastName;

    @NotEmpty
    @Size(min = 11, max = 11)
    String peselNumber;

    @NotEmpty
    @Size(min = 9, max = 9)
    String phone;
}
