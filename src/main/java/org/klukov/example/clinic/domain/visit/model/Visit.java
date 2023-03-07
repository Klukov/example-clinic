package org.klukov.example.clinic.domain.visit.model;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Visit {
    @NonNull VisitId id;
    @NonNull LocalDateTime from;
    @NonNull LocalDateTime to;
    @NonNull DoctorId doctorId;
    @NonNull VisitStatus status;

    @Getter(AccessLevel.NONE)
    PatientId patientId;

    String patientRemarks;

    public Optional<PatientId> getPatientId() {
        return Optional.ofNullable(patientId);
    }
}
