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

    def generateSampleData() {
        def doctor1 = generateDoctor("Janusz", "Pracz")
        def doctor2 = generateDoctor("Grazyna", "Macz")
        generateFreeVisit(now().plusDays(1), doctor1)
        generateFreeVisit(now().plusDays(1).plusHours(2), doctor1)
        generateFreeVisit(now().plusDays(1).plusHours(1), doctor2)
        generateFreeVisit(now().plusDays(1).plusHours(3), doctor2)
    }


    static LocalDateTime now() {
        LocalDateTime.of(2022, 2, 2, 12, 0, 0)
    }

    DoctorDao generateDoctor(String firstName, String lastName) {
        doctorJpaRepository.save(
                new DoctorDao(
                        firstName: firstName,
                        lastName: lastName,
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
