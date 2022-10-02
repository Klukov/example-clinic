package org.klukov.example.clinic.domain.doctor.out;

import org.klukov.example.clinic.domain.doctor.Doctor;
import org.klukov.example.clinic.domain.doctor.DoctorId;
import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;

import java.util.Collection;
import java.util.Set;

public interface DoctorRepository {

    Set<Doctor> findDoctors(Collection<DoctorId> ids, Collection<DoctorSpecialization> doctorSpecializations);
}
