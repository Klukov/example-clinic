package org.klukov.example.clinic

import org.klukov.example.clinic.domain.VisitStatus
import org.klukov.example.clinic.repository.dao.DoctorDao
import org.klukov.example.clinic.repository.dao.VisitDao
import org.klukov.example.clinic.repository.db.DoctorJpaRepository
import org.klukov.example.clinic.repository.db.PatientJpaRepository
import org.klukov.example.clinic.repository.db.VisitJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDateTime

@Component
class DataGenerator {

    @Autowired
    VisitJpaRepository visitJpaRepository

    @Autowired
    DoctorJpaRepository doctorJpaRepository

    @Autowired
    PatientJpaRepository patientJpaRepository

    def cleanup() {
        patientJpaRepository.deleteAll()
        doctorJpaRepository.deleteAll()
        visitJpaRepository.deleteAll()
    }

    def generateSampleData() {
        def doctor1 = generateDoctor("Janusz", "Pracz", BigDecimal.valueOf(40L))
        def doctor2 = generateDoctor("Grazyna", "Macz", BigDecimal.valueOf(60L))
        def doctor3 = generateDoctor("Nova", "Super", null)

        // DAY 1
        generateFreeVisit(now().plusDays(1), doctor1)
        generateFreeVisit(now().plusDays(1).plusHours(2), doctor1)
        generateFreeVisit(now().plusDays(1).plusHours(1), doctor2)
        generateFreeVisit(now().plusDays(1).plusHours(3), doctor2)

        // DAY 2
        generateFreeVisit(now().plusDays(2), doctor1)
        generateFreeVisit(now().plusDays(2).plusHours(2), doctor1)
        generateFreeVisit(now().plusDays(2).plusHours(1), doctor3) // new doctor
        generateFreeVisit(now().plusDays(2).plusHours(3), doctor3) // new doctor
        generateFreeVisit(now().plusDays(2).plusHours(4), doctor2)
        generateFreeVisit(now().plusDays(2).plusHours(5), doctor2)

        // DAY 3
        generateFreeVisit(now().plusDays(3), doctor1)
        generateFreeVisit(now().plusDays(3).plusHours(2), doctor1)
        generateFreeVisit(now().plusDays(3).plusHours(1), doctor2)
        generateFreeVisit(now().plusDays(3).plusHours(3), doctor2)
    }


    static LocalDateTime now() {
        LocalDateTime.of(2022, 2, 2, 12, 0, 0)
    }

    DoctorDao generateDoctor(String firstName, String lastName, BigDecimal rating) {
        doctorJpaRepository.save(
                new DoctorDao(
                        firstName: firstName,
                        lastName: lastName,
                        rating: rating,
                )
        )
    }

    VisitDao generateFreeVisit(LocalDateTime from, DoctorDao doctorDao) {
        visitJpaRepository.save(
                new VisitDao(
                        doctorId: doctorDao.id,
                        timeFrom: from,
                        timeTo: from.plusHours(1),
                        status: VisitStatus.FREE
                )
        )
    }
}
