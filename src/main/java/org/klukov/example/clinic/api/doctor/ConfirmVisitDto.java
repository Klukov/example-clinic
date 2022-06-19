package org.klukov.example.clinic.api.doctor;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
class ConfirmVisitDto {
    Long visitId;
}
