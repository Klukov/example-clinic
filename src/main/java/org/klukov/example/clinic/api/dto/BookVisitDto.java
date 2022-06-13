package org.klukov.example.clinic.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class BookVisitDto {
    Long visitId;
    String PatientDto;
}
