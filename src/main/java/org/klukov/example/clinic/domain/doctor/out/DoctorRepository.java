package org.klukov.example.clinic.domain.doctor.out;

import java.util.Collection;
import java.util.Set;
import org.klukov.example.clinic.domain.doctor.model.Doctor;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization;

public interface DoctorRepository {

    Set<Doctor> findDoctors(
            Collection<DoctorId> ids, Collection<DoctorSpecialization> doctorSpecializations);
}
