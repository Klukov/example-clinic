package org.klukov.example.clinic.repository.visit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.springframework.lang.NonNull;

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
@Table(name = "visit")
@ToString
class VisitDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;
    private Long patientId;

    @NonNull
    private LocalDateTime timeFrom;

    @NonNull
    private LocalDateTime timeTo;

    @NonNull
    private VisitStatus status;

    private String patientRemarks;

    public static VisitDao fromDomain(Visit visit) {
        return VisitDao.builder()
                .id(visit.getId())
                .doctorId(visit.getDoctorId())
                .patientId(visit.getPatientId())
                .timeFrom(visit.getFrom())
                .timeTo(visit.getTo())
                .status(visit.getStatus())
                .patientRemarks(visit.getPatientRemarks())
                .build();
    }

    public Visit toDomain() {
        return Visit.builder()
                .id(getId())
                .from(getTimeFrom())
                .to(getTimeTo())
                .status(getStatus())
                .doctorId(getDoctorId())
                .patientId(getPatientId())
                .patientRemarks(getPatientRemarks())
                .build();
    }
}
