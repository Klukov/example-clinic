package org.klukov.example.clinic.repository;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.Patient;
import org.klukov.example.clinic.repository.dao.PatientDao;
import org.klukov.example.clinic.repository.db.PatientJpaRepository;
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
