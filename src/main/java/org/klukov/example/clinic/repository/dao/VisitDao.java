package org.klukov.example.clinic.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.klukov.example.clinic.domain.Visit;
import org.klukov.example.clinic.domain.VisitStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "visits")
public class VisitDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId; // todo: make Doctor relation
    private Long patientId; // todo: make Patient relation

    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;

    private VisitStatus status;

    public static VisitDao fromDomain(Visit visit) {
        return VisitDao.builder()
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
                .visitStatus(getStatus())
                .doctorId(getDoctorId())
                .patientId(getPatientId())
                .build();
    }
}
