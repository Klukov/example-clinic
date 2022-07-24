package org.klukov.example.clinic.repository.patient

import org.klukov.example.clinic.domain.visit.Patient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PatientDataGenerator {

    @Autowired
    PatientJpaRepository patientJpaRepository

    def cleanup() {
        patientJpaRepository.deleteAll()
    }

    Patient generatePatient(String firstName, String lastName, String peselNumber) {
        patientJpaRepository.save(
                new PatientDao(
                        firstName: firstName,
                        lastName: lastName,
                        peselNumber: peselNumber,
                )
        ).toDomain()
    }
}
