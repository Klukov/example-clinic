package org.klukov.example.clinic.repository.patient

import org.klukov.example.clinic.domain.visit.model.Patient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PatientDataGenerator {

    @Autowired
    PatientJpaRepository patientJpaRepository

    def cleanup() {
        patientJpaRepository.deleteAll()
    }

    Patient generatePatient(String firstName, String lastName, String peselNumber, String phone) {
        patientJpaRepository.save(
                new PatientDao(
                        firstName: firstName,
                        lastName: lastName,
                        peselNumber: peselNumber,
                        phone: phone,
                )
        ).toDomain()
    }
}
