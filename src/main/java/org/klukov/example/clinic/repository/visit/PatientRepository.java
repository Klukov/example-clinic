package org.klukov.example.clinic.repository.visit;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.visit.Patient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientRepository {

    private final PatientJpaRepository patientJpaRepository;

    public Patient save(Patient patient) {
        return patientJpaRepository.save(PatientModel.fromDomain(patient)).toDomain();
    }
}
