package org.klukov.example.clinic.api.visit;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
class BookVisitDto {
    Long visitId;
    String PatientDto;
}
