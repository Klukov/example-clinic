package org.klukov.example.clinic.repository.visit

import org.klukov.example.clinic.domain.doctor.Doctor
import org.klukov.example.clinic.domain.visit.VisitStatus
import org.klukov.example.clinic.repository.doctor.DoctorDataGenerator
import org.klukov.example.clinic.repository.visit.PatientJpaRepository
import org.klukov.example.clinic.repository.visit.VisitJpaRepository
import org.klukov.example.clinic.repository.visit.VisitModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDateTime

@Component
class VisitDataGenerator {

    @Autowired
    VisitJpaRepository visitJpaRepository

    @Autowired
    PatientJpaRepository patientJpaRepository

    @Autowired
    DoctorDataGenerator doctorDataGenerator

    def cleanup() {
        patientJpaRepository.deleteAll()
        visitJpaRepository.deleteAll()
        doctorDataGenerator.cleanup()
    }

    def generateSampleData() {
        def doctor1 = doctorDataGenerator.generateDoctor("Janusz", "Pracz", 40)
        def doctor2 = doctorDataGenerator.generateDoctor("Grazyna", "Macz", 60)
        def doctor3 = doctorDataGenerator.generateDoctor("Nova", "Super", null)

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

    VisitModel generateFreeVisit(LocalDateTime from, Doctor doctor) {
        visitJpaRepository.save(
                new VisitModel(
                        doctorId: doctor.getId(),
                        timeFrom: from,
                        timeTo: from.plusHours(1),
                        status: VisitStatus.FREE
                )
        )
    }
}
