package org.klukov.example.clinic

import org.klukov.example.clinic.domain.DoctorSpecialization
import org.klukov.example.clinic.domain.VisitStatus
import org.klukov.example.clinic.repository.dao.DoctorDao
import org.klukov.example.clinic.repository.dao.PatientDao
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

        def patient1 = generatePatient("Aaaa", "Bbbbb", "11111111111")
        def patient2 = generatePatient("Cccc", "Ddddd", "22222222222")
        def patient3 = generatePatient("Eeee", "Fffff", "33333333333")
        def patient4 = generatePatient("Gggg", "Hhhhh", "44444444444")
        def patient5 = generatePatient("Iiii", "Jjjjj", "55555555555")
        def patient6 = generatePatient("Kkkk", "Lllll", "66666666666")
        def patient7 = generatePatient("Mmmm", "Nnnnn", "77777777777")
        def patient8 = generatePatient("Oooo", "Ppppp", "88888888888")

        // DAY 1
        generateVisit(now().plusDays(1), doctor1)
        generateVisit(now().plusDays(1).plusHours(1), doctor2, VisitStatus.OCCUPIED, patient1)
        generateVisit(now().plusDays(1).plusHours(2), doctor1, VisitStatus.CONFIRMED, patient2)
        generateVisit(now().plusDays(1).plusHours(3), doctor2)

        // DAY 2
        generateVisit(now().plusDays(2), doctor1, VisitStatus.OCCUPIED, patient3)
        generateVisit(now().plusDays(2).plusHours(1), doctor3) // new doctor
        generateVisit(now().plusDays(2).plusHours(2), doctor1, VisitStatus.CONFIRMED, patient4)
        generateVisit(now().plusDays(2).plusHours(3), doctor3) // new doctor
        generateVisit(now().plusDays(2).plusHours(4), doctor2, VisitStatus.CONFIRMED, patient5)
        generateVisit(now().plusDays(2).plusHours(5), doctor2, VisitStatus.OCCUPIED, patient6)

        // DAY 3
        generateVisit(now().plusDays(3), doctor1, VisitStatus.CONFIRMED, patient7)
        generateVisit(now().plusDays(3).plusHours(1), doctor2)
        generateVisit(now().plusDays(3).plusHours(2), doctor1)
        generateVisit(now().plusDays(3).plusHours(3), doctor2, VisitStatus.OCCUPIED, patient8)
    }


    static LocalDateTime now() {
        LocalDateTime.of(2022, 2, 2, 12, 0, 0)
    }

    private DoctorDao generateDoctor(String firstName, String lastName, BigDecimal rating) {
        doctorJpaRepository.save(
                new DoctorDao(
                        firstName: firstName,
                        lastName: lastName,
                        rating: rating,
                        specialization: DoctorSpecialization.DENTIST
                )
        )
    }

    private VisitDao generateVisit(
            LocalDateTime from,
            DoctorDao doctorDao,
            VisitStatus visitStatus = VisitStatus.FREE,
            PatientDao patientDao = null
    ) {
        visitJpaRepository.save(
                new VisitDao(
                        doctorId: doctorDao.id,
                        patientId: patientDao == null ? null : patientDao.id,
                        timeFrom: from,
                        timeTo: from.plusHours(1),
                        status: visitStatus,
                )
        )
    }

    private PatientDao generatePatient(String firstName, String lastName, String peselNumber) {
        patientJpaRepository.save(
                new PatientDao(
                        firstName: firstName,
                        lastName: lastName,
                        peselNumber: peselNumber,
                )
        )
    }
}
