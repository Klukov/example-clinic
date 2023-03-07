package org.klukov.example.clinic.api.patient;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
class BookVisitDto {

    @NotNull PatientDto patient;

    String remarks;
}
