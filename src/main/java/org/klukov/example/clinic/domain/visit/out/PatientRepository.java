package org.klukov.example.clinic.domain.visit.out;

import org.klukov.example.clinic.domain.visit.NewPatient;
import org.klukov.example.clinic.domain.visit.Patient;
import org.klukov.example.clinic.domain.visit.PatientId;

import java.util.Optional;

public interface PatientRepository {

    Optional<Patient> findById(PatientId patientId);

    Patient update(Patient patient);

    Patient save(NewPatient newPatient);
}
