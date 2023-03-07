package org.klukov.example.clinic.domain.doctor.in;

import java.util.Set;
import org.klukov.example.clinic.domain.doctor.model.Doctor;

public interface AvailableDoctorsUseCase {

    Set<Doctor> findAll(AvailableDoctorsQuery availableDoctorsQuery);
}
