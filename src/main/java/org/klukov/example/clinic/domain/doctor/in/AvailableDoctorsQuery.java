package org.klukov.example.clinic.domain.doctor.in;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AvailableDoctorsQuery {
    LocalDateTime from;
    LocalDateTime to;
    Set<DoctorSpecialization> doctorSpecializations;
}
