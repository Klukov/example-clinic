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
        def doctors = generateDoctors()
        def doctorNameMap = doctors
                .collectEntries { [it.firstName + " " + it.lastName, it] } as Map<String, DoctorDao>
        def doctorIdMap = doctors.collectEntries { [it.id, it] } as Map<Long, DoctorDao>

        def patients = generatePatients()
        def patientMap = patients.collectEntries { [it.id, it] } as Map<Long, PatientDao>

        def visitList = generateVisits(doctors, patients)
        def visitMap = visitList.collectEntries { [it.id, it] } as Map<Long, VisitDao>

        return [
                "doctorsById"  : doctorIdMap,
                "doctorsByName": doctorNameMap,
                "patientsById" : patientMap,
                "visitsById"   : visitMap,
        ]
    }

    private List<VisitDao> generateVisits(List<DoctorDao> doctors, List<PatientDao> patients) {
        [
                // DAY 1
                generateVisit(now().plusDays(1), doctors[0]),
                generateVisit(now().plusDays(1).plusHours(1), doctors[1], VisitStatus.OCCUPIED, patients[0]),
                generateVisit(now().plusDays(1).plusHours(2), doctors[0], VisitStatus.CONFIRMED, patients[1]),
                generateVisit(now().plusDays(1).plusHours(3), doctors[1]),

                // DAY 2
                generateVisit(now().plusDays(2), doctors[0], VisitStatus.OCCUPIED, patients[2]),
                generateVisit(now().plusDays(2).plusHours(1), doctors[2]), // new doctor
                generateVisit(now().plusDays(2).plusHours(2), doctors[0], VisitStatus.CONFIRMED, patients[3]),
                generateVisit(now().plusDays(2).plusHours(3), doctors[2]), // new doctor
                generateVisit(now().plusDays(2).plusHours(4), doctors[1], VisitStatus.CONFIRMED, patients[4]),
                generateVisit(now().plusDays(2).plusHours(5), doctors[1], VisitStatus.OCCUPIED, patients[5]),

                // DAY 3
                generateVisit(now().plusDays(3), doctors[0], VisitStatus.CONFIRMED, patients[6]),
                generateVisit(now().plusDays(3).plusHours(1), doctors[1]),
                generateVisit(now().plusDays(3).plusHours(2), doctors[0]),
                generateVisit(now().plusDays(3).plusHours(3), doctors[1], VisitStatus.OCCUPIED, patients[7]),
        ]
    }

    private List<PatientDao> generatePatients() {
        [
                generatePatient("Aaaa", "Bbbbb", "11111111111"),
                generatePatient("Cccc", "Ddddd", "22222222222"),
                generatePatient("Eeee", "Fffff", "33333333333"),
                generatePatient("Gggg", "Hhhhh", "44444444444"),
                generatePatient("Iiii", "Jjjjj", "55555555555"),
                generatePatient("Kkkk", "Lllll", "66666666666"),
                generatePatient("Mmmm", "Nnnnn", "77777777777"),
                generatePatient("Oooo", "Ppppp", "88888888888"),
        ]
    }

    private List<DoctorDao> generateDoctors() {
        [
                generateDoctor("Janusz", "Pracz", BigDecimal.valueOf(40L)),
                generateDoctor("Grazyna", "Macz", BigDecimal.valueOf(60L)),
                generateDoctor("Nova", "Super", null),
        ]
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
