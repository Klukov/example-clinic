package org.klukov.example.clinic.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ConfirmVisitDto {
    Long visitId;
}
