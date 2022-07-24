package org.klukov.example.clinic.api.patient;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@Jacksonized
@Builder
class BookVisitDto {

    @NotNull
    PatientDto patient;

    String remarks;
}
