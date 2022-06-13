package org.klukov.example.clinic.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.klukov.example.clinic.domain.Visit;
import org.klukov.example.clinic.domain.VisitStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VisitDao {

    @Id
    private Long id;

//    @ManyToOne
//    private DoctorDao doctor;

    private Long doctorId;
    private Long patientId; // todo: make Patient relation

    private LocalDateTime from;
    private LocalDateTime to;

    private VisitStatus status;

    public static VisitDao fromDomain(Visit visit) {
        return VisitDao.builder()
                .id(visit.getId())
                .doctorId(visit.getDoctorId())
                .patientId(visit.getPatientId())
                .from(visit.getFrom())
                .to(visit.getTo())
                .status(visit.getVisitStatus())
                .build();
    }

    public Visit toDomain() {
        return Visit.builder()
                .id(getId())
                .from(getFrom())
                .to(getTo())
                .doctorId(getDoctorId())
                .patientId(getPatientId())
                .build();
    }
}
