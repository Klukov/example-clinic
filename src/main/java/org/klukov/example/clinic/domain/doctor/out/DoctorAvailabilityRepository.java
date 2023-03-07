package org.klukov.example.clinic.domain.doctor.out;

import java.time.LocalDateTime;
import java.util.Set;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;

public interface DoctorAvailabilityRepository {

    Set<DoctorId> findAllAvailableDoctors(LocalDateTime from, LocalDateTime to);
}
