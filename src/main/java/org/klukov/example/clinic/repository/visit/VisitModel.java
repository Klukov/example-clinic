package org.klukov.example.clinic.repository.visit;

import lombok.*;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "visits")
class VisitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId; // todo: make Doctor relation
    private Long patientId; // todo: make Patient relation

    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;

    private VisitStatus status;

    public static VisitModel fromDomain(Visit visit) {
        return VisitModel.builder()
                .id(visit.getId())
                .doctorId(visit.getDoctorId())
                .patientId(visit.getPatientId())
                .timeFrom(visit.getFrom())
                .timeTo(visit.getTo())
                .status(visit.getVisitStatus())
                .build();
    }

    public Visit toDomain() {
        return Visit.builder()
                .id(getId())
                .from(getTimeFrom())
                .to(getTimeTo())
                .doctorId(getDoctorId())
                .patientId(getPatientId())
                .build();
    }
}
