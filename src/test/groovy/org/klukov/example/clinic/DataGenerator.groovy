package org.klukov.example.clinic

import org.klukov.example.clinic.domain.*
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
        def doctorIdMap = doctors.collectEntries { [it.id, it] } as Map<Long, Doctor>

        def patients = generatePatients()
        def patientMap = patients.collectEntries { [it.id, it] } as Map<Long, Patient>

        def visitList = generateVisits(doctors, patients)
        def visitMap = visitList.collectEntries { [it.id, it] } as Map<Long, Visit>

        return [
                "doctorsById"  : doctorIdMap,
                "doctorsByName": doctorNameMap,
                "patientsById" : patientMap,
                "visitsById"   : visitMap,
        ]
    }

    private List<Visit> generateVisits(List<Doctor> doctors, List<Patient> patients) {
        [
                // DAY 1
                generateVisit(now().plusDays(1), doctors[0].id),
                generateVisit(now().plusDays(1).plusHours(1), doctors[1].id, VisitStatus.OCCUPIED, patients[0].id),
                generateVisit(now().plusDays(1).plusHours(2), doctors[0].id, VisitStatus.CONFIRMED, patients[1].id),
                generateVisit(now().plusDays(1).plusHours(3), doctors[1].id),

                // DAY 2
                generateVisit(now().plusDays(2), doctors[0].id, VisitStatus.OCCUPIED, patients[2].id),
                generateVisit(now().plusDays(2).plusHours(1), doctors[2].id), // new doctor
                generateVisit(now().plusDays(2).plusHours(2), doctors[0].id, VisitStatus.CONFIRMED, patients[3].id),
                generateVisit(now().plusDays(2).plusHours(3), doctors[2].id), // new doctor
                generateVisit(now().plusDays(2).plusHours(4), doctors[1].id, VisitStatus.CONFIRMED, patients[4].id),
                generateVisit(now().plusDays(2).plusHours(5), doctors[1].id, VisitStatus.OCCUPIED, patients[5].id),

                // DAY 3
                generateVisit(now().plusDays(3), doctors[0].id, VisitStatus.CONFIRMED, patients[6].id),
                generateVisit(now().plusDays(3).plusHours(1), doctors[1].id),
                generateVisit(now().plusDays(3).plusHours(2), doctors[0].id),
                generateVisit(now().plusDays(3).plusHours(3), doctors[1].id, VisitStatus.OCCUPIED, patients[7].id),
        ]
    }

    private List<Patient> generatePatients() {
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

    private List<Doctor> generateDoctors() {
        [
                generateDoctor("Janusz", "Pracz", BigDecimal.valueOf(40L)),
                generateDoctor("Grazyna", "Macz", BigDecimal.valueOf(60L)),
                generateDoctor("Nova", "Super", null),
        ]
    }


    static LocalDateTime now() {
        LocalDateTime.of(2022, 2, 2, 12, 0, 0)
    }

    private Doctor generateDoctor(String firstName, String lastName, BigDecimal rating) {
        doctorJpaRepository.save(
                new DoctorDao(
                        firstName: firstName,
                        lastName: lastName,
                        rating: rating,
                        specialization: DoctorSpecialization.DENTIST
                )
        ).toDomain()
    }

    private Visit generateVisit(
            LocalDateTime from,
            Long doctorId,
            VisitStatus visitStatus = VisitStatus.FREE,
            Long patientId = null
    ) {
        visitJpaRepository.save(
                new VisitDao(
                        doctorId: doctorId,
                        patientId: patientId,
                        timeFrom: from,
                        timeTo: from.plusHours(1),
                        status: visitStatus,
                )
        ).toDomain()
    }

    private Patient generatePatient(String firstName, String lastName, String peselNumber) {
        patientJpaRepository.save(
                new PatientDao(
                        firstName: firstName,
                        lastName: lastName,
                        peselNumber: peselNumber,
                )
        ).toDomain()
    }
}
