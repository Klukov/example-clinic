package org.klukov.example.clinic.domain.doctor.in;

import org.klukov.example.clinic.domain.doctor.Doctor;

import java.util.Set;

public interface AvailableDoctorsUseCase {

    Set<Doctor> findAll(AvailableDoctorsQuery availableDoctorsQuery);
}
