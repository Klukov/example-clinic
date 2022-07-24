package org.klukov.example.clinic.repository.patient;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.visit.Patient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatientRepository {

    private final PatientJpaRepository patientJpaRepository;

    public Optional<Patient> findById(Long id) {
        return patientJpaRepository.findById(id)
                .map(PatientDao::toDomain);
    }

    public Patient save(Patient patient) {
        return patientJpaRepository.save(PatientDao.fromDomain(patient)).toDomain();
    }
}
