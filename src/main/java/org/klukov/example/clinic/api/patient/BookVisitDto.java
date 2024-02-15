package org.klukov.example.clinic.api.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
class BookVisitDto {

    @NotNull @Valid PatientDto patient;

    String remarks;
}
