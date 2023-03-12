package org.klukov.example.clinic

import static org.klukov.example.clinic.TestSettings.now

import org.klukov.example.clinic.domain.doctor.model.Doctor
import org.klukov.example.clinic.domain.visit.model.Patient
import org.klukov.example.clinic.domain.visit.model.Visit
import org.klukov.example.clinic.domain.visit.model.VisitStatus
import org.klukov.example.clinic.repository.doctor.DoctorDataGenerator
import org.klukov.example.clinic.repository.patient.PatientDataGenerator
import org.klukov.example.clinic.repository.visit.VisitDataGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DataGenerator {

    @Autowired
    VisitDataGenerator visitDataGenerator

    @Autowired
    DoctorDataGenerator doctorDataGenerator

    @Autowired
    PatientDataGenerator patientDataGenerator

    def cleanup() {
        patientDataGenerator.cleanup()
        doctorDataGenerator.cleanup()
        visitDataGenerator.cleanup()
    }

    @Transactional
    def generateSampleData() {
        def doctors = generateDoctors()
        def doctorNameMap = doctors
                .collectEntries { [it.firstName + " " + it.lastName, it] } as Map<String, Doctor>
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
                visitDataGenerator.generateVisit(now().plusDays(1), doctors[0].id),
                visitDataGenerator.generateVisit(now().plusDays(1).plusHours(1), doctors[1].id, VisitStatus.OCCUPIED, patients[0].id.value),
                visitDataGenerator.generateVisit(now().plusDays(1).plusHours(2), doctors[0].id, VisitStatus.CONFIRMED, patients[1].id.value),
                visitDataGenerator.generateVisit(now().plusDays(1).plusHours(3), doctors[1].id),

                // DAY 2
                visitDataGenerator.generateVisit(now().plusDays(2), doctors[0].id, VisitStatus.OCCUPIED, patients[2].id.value),
                visitDataGenerator.generateVisit(now().plusDays(2).plusHours(1), doctors[2].id), // new doctor
                visitDataGenerator.generateVisit(now().plusDays(2).plusHours(2), doctors[0].id, VisitStatus.CONFIRMED, patients[3].id.value),
                visitDataGenerator.generateVisit(now().plusDays(2).plusHours(3), doctors[2].id), // new doctor
                visitDataGenerator.generateVisit(now().plusDays(2).plusHours(4), doctors[1].id, VisitStatus.CONFIRMED, patients[4].id.value),
                visitDataGenerator.generateVisit(now().plusDays(2).plusHours(5), doctors[1].id, VisitStatus.OCCUPIED, patients[5].id.value),

                // DAY 3
                visitDataGenerator.generateVisit(now().plusDays(3), doctors[0].id, VisitStatus.CONFIRMED, patients[6].id.value),
                visitDataGenerator.generateVisit(now().plusDays(3).plusHours(1), doctors[1].id),
                visitDataGenerator.generateVisit(now().plusDays(3).plusHours(2), doctors[0].id),
                visitDataGenerator.generateVisit(now().plusDays(3).plusHours(3), doctors[1].id, VisitStatus.OCCUPIED, patients[7].id.value),
        ]
    }

    private List<Patient> generatePatients() {
        [
                patientDataGenerator.generatePatient("Aaaa", "Bbbbb", "11111111111", "111111111"),
                patientDataGenerator.generatePatient("Cccc", "Ddddd", "22222222222", "222222222"),
                patientDataGenerator.generatePatient("Eeee", "Fffff", "33333333333", "333333333"),
                patientDataGenerator.generatePatient("Gggg", "Hhhhh", "44444444444", "444444444"),
                patientDataGenerator.generatePatient("Iiii", "Jjjjj", "55555555555", "555555555"),
                patientDataGenerator.generatePatient("Kkkk", "Lllll", "66666666666", "666666666"),
                patientDataGenerator.generatePatient("Mmmm", "Nnnnn", "77777777777", "777777777"),
                patientDataGenerator.generatePatient("Oooo", "Ppppp", "88888888888", "888888888"),
        ]
    }

    private List<Doctor> generateDoctors() {
        [
                doctorDataGenerator.generateDoctor("Janusz", "Pracz", BigDecimal.valueOf(40L)),
                doctorDataGenerator.generateDoctor("Grazyna", "Macz", BigDecimal.valueOf(60L)),
                doctorDataGenerator.generateDoctor("Nova", "Super", null),
        ]
    }
}
