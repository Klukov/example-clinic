package org.klukov.example.clinic.repository.visit;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.visit.model.PatientId;
import org.klukov.example.clinic.domain.visit.model.Visit;
import org.klukov.example.clinic.domain.visit.model.VisitId;
import org.klukov.example.clinic.domain.visit.model.VisitStatus;
import org.springframework.lang.NonNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "visit")
class VisitDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;
    private Long patientId;

    @NonNull private LocalDateTime timeFrom;

    @NonNull private LocalDateTime timeTo;

    @NonNull @Enumerated(EnumType.STRING)
    private VisitStatus status;

    private String patientRemarks;

    public static VisitDao fromDomain(Visit visit) {
        var builder =
                VisitDao.builder()
                        .doctorId(visit.getDoctorId().getValue())
                        .timeFrom(visit.getFrom())
                        .timeTo(visit.getTo())
                        .status(visit.getStatus())
                        .patientRemarks(visit.getPatientRemarks());
        if (visit.getPatientId().isPresent()) {
            builder.patientId(visit.getPatientId().get().getValue());
        }
        return builder.build();
    }

    public Visit toDomain() {
        return Visit.builder()
                .id(VisitId.of(id))
                .from(timeFrom)
                .to(timeTo)
                .status(status)
                .doctorId(DoctorId.of(doctorId))
                .patientId(patientId == null ? null : PatientId.of(patientId))
                .patientRemarks(patientRemarks)
                .build();
    }
}
