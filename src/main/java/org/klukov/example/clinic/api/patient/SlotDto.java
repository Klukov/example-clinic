package org.klukov.example.clinic.api.patient;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.visit.model.Visit;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
class SlotDto {
    Long id;
    LocalDateTime from;
    LocalDateTime to;
    Long doctorId;

    public static SlotDto fromDomain(Visit visit) {
        return SlotDto.builder()
                .id(visit.getId().getValue())
                .from(visit.getFrom())
                .to(visit.getTo())
                .doctorId(visit.getDoctorId().getValue())
                .build();
    }
}
