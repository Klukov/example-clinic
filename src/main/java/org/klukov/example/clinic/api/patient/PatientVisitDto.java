package org.klukov.example.clinic.api.patient;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitStatus;

import java.time.LocalDateTime;

@Value
@Jacksonized
@Builder
class PatientVisitDto {

    Long id;
    LocalDateTime from;
    LocalDateTime to;
    Long doctorId;
    Long patientId;
    VisitStatus visitStatus;

    public static PatientVisitDto fromDomain(Visit visit) {
        return PatientVisitDto.builder()
                .id(visit.getId())
                .from(visit.getFrom())
                .to(visit.getTo())
                .doctorId(visit.getDoctorId())
                .patientId(visit.getPatientId())
                .visitStatus(visit.getStatus())
                .build();
    }
}
