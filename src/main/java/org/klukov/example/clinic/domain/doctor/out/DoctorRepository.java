package org.klukov.example.clinic.domain.doctor.out;

import org.klukov.example.clinic.domain.doctor.model.Doctor;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization;

import java.util.Collection;
import java.util.Set;

public interface DoctorRepository {

    Set<Doctor> findDoctors(Collection<DoctorId> ids, Collection<DoctorSpecialization> doctorSpecializations);
}
