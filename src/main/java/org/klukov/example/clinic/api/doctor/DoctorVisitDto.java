package org.klukov.example.clinic.api.doctor;

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
class DoctorVisitDto {

    Long id;
    LocalDateTime from;
    LocalDateTime to;
    Long doctorId;
    Long patientId;
    VisitStatus visitStatus;

    public static DoctorVisitDto fromDomain(Visit visit) {
        return DoctorVisitDto.builder()
                .id(visit.getId().getValue())
                .from(visit.getFrom())
                .to(visit.getTo())
                .doctorId(visit.getDoctorId().getValue())
                .patientId(visit.getPatientId().map(PatientId::getValue).orElse(null))
                .visitStatus(visit.getStatus())
                .build();
    }
}
