package org.klukov.example.clinic.api.visit;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.visit.Visit;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
class SlotDto {
    Long id;
    LocalDateTime from;
    LocalDateTime to;
    Long doctorId;

    static SlotDto fromDomain(Visit visit) {
        return SlotDto.builder()
                .id(visit.getId())
                .from(visit.getFrom())
                .to(visit.getTo())
                .doctorId(visit.getDoctorId())
                .build();
    }
}
