package org.klukov.example.clinic.repository.patient;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.exception.ClinicRuntimeException;
import org.klukov.example.clinic.domain.visit.model.NewPatient;
import org.klukov.example.clinic.domain.visit.model.Patient;
import org.klukov.example.clinic.domain.visit.model.PatientId;
import org.klukov.example.clinic.domain.visit.out.PatientRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PatientRepositoryImpl implements PatientRepository {

    private final PatientJpaRepository patientJpaRepository;

    @Override
    public Optional<Patient> findById(PatientId patientId) {
        return patientJpaRepository.findById(patientId.getValue()).map(PatientDao::toDomain);
    }

    @Override
    public Patient update(Patient patient) {
        var updatedPatient =
                Optional.of(patient.getId())
                        .map(PatientId::getValue)
                        .map(patientJpaRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(PatientDao::toBuilder)
                        .orElseThrow(
                                () ->
                                        new ClinicRuntimeException(
                                                String.format(
                                                        "Patient not found: %s", patient.getId())))
                        .peselNumber(patient.getPeselNumber())
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .build();
        return patientJpaRepository.save(updatedPatient).toDomain();
    }

    @Override
    public Patient save(NewPatient newPatient) {
        return patientJpaRepository.save(PatientDao.fromDomain(newPatient)).toDomain();
    }
}
