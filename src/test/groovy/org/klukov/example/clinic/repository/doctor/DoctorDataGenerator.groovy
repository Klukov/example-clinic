package org.klukov.example.clinic.repository.doctor

import org.klukov.example.clinic.domain.doctor.model.Doctor
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DoctorDataGenerator {

    @Autowired
    DoctorJpaRepository doctorJpaRepository

    def cleanup() {
        doctorJpaRepository.deleteAll()
    }

    Doctor generateDoctor(String firstName, String lastName, BigDecimal rating) {
        doctorJpaRepository.save(
                new DoctorDao(
                        firstName: firstName,
                        lastName: lastName,
                        rating: rating,
                        specialization: DoctorSpecialization.DENTIST
                )
        ).toDomain()
    }
}
