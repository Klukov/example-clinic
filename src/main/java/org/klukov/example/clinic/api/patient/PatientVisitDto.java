package org.klukov.example.clinic.api.patient;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.visit.model.PatientId;
import org.klukov.example.clinic.domain.visit.model.Visit;
import org.klukov.example.clinic.domain.visit.model.VisitStatus;

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
                .id(visit.getId().getValue())
                .from(visit.getFrom())
                .to(visit.getTo())
                .doctorId(visit.getDoctorId().getValue())
                .patientId(visit.getPatientId().map(PatientId::getValue).orElse(null))
                .visitStatus(visit.getStatus())
                .build();
    }
}
