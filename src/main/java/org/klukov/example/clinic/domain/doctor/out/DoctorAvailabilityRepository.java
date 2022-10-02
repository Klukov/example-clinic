package org.klukov.example.clinic.domain.doctor.out;

import org.klukov.example.clinic.domain.doctor.DoctorId;

import java.time.LocalDateTime;
import java.util.Set;

public interface DoctorAvailabilityRepository {

    Set<DoctorId> findAllAvailableDoctors(LocalDateTime from, LocalDateTime to);
}
