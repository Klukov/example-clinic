package org.klukov.example.clinic.api.controller


import groovy.util.logging.Slf4j
import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.VisitRestApi
import org.klukov.example.clinic.api.dto.BookVisitDto
import org.klukov.example.clinic.api.dto.DoctorDto
import org.klukov.example.clinic.api.dto.PatientDto
import org.klukov.example.clinic.domain.Patient
import org.klukov.example.clinic.repository.PatientRepository
import org.klukov.example.clinic.repository.VisitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class VisitControllerTest extends Specification {

    @Autowired
    DataGenerator dataGenerator

    @Autowired
    VisitRestApi visitRestApi

    @Autowired
    VisitRepository visitRepository

    @Autowired
    PatientRepository patientRepository

    def cleanup() {
        dataGenerator.cleanup()
    }

    def "Should return only available doctors in specific time"() {
        given:
        dataGenerator.generateSampleData()

        when:
        def result = visitRestApi.queryDoctors(queryFrom, queryTo)

        then:
        result.size() == expectedDoctors.size()
        (0..<expectedDoctors.size()).forEach(it -> assertDoctorDto(result[it], expectedDoctors[it][0], expectedDoctors[it][1]))

        where:
        queryFrom          | queryTo            || expectedDoctors
        "2019-05-01T23:10" | "2023-05-01T23:10" || [["Grazyna", "Macz"], ["Janusz", "Pracz"], ["Nova", "Super"]]
        "2022-02-03T11:10" | "2022-02-03T18:00" || [["Grazyna", "Macz"], ["Janusz", "Pracz"]]
        "2022-02-04T12:00" | "2022-02-04T18:00" || [["Nova", "Super"]]
        "2022-02-05T12:00" | "2022-02-05T16:00" || [["Grazyna", "Macz"], ["Janusz", "Pracz"]]
        "2022-02-05T13:00" | "2022-02-05T14:00" || [["Grazyna", "Macz"]]
    }

    private void assertDoctorDto(DoctorDto doctorDto, firstName, lastName) {
        assert doctorDto.id != null
        assert doctorDto.firstName == firstName
        assert doctorDto.lastName == lastName
    }

    def "should return available visits for queried time and doctor"() {
        given:
        dataGenerator.generateSampleData()

        when:
        def doctors = visitRestApi.queryDoctors(queryFrom, queryTo)
        def doctorId = findDoctorId(doctors, queriedDoctorName[0], queriedDoctorName[1])
        def result = visitRestApi.queryVisits(queryFrom, queryTo, doctorId)

        then:
        result.size() == expectedNumberOfAvailableVisits

        where:
        queryFrom          | queryTo            | queriedDoctorName   || expectedNumberOfAvailableVisits
        "2019-05-01T23:10" | "2023-05-01T23:10" | ["Janusz", "Pracz"] || 2
        "2019-05-01T23:10" | "2023-05-01T23:10" | ["Grazyna", "Macz"] || 2
        "2019-05-01T23:10" | "2023-05-01T23:10" | ["Nova", "Super"]   || 2
    }

    private Long findDoctorId(List<DoctorDto> doctors, String firstName, String lastName) {
        doctors.stream()
                .filter(Objects::nonNull)
                .filter(doctor -> doctor.firstName == firstName)
                .filter(doctor -> doctor.lastName == lastName)
                .map(DoctorDto::getId)
                .findFirst()
                .get()
    }

    def "should visit be booked, when patient register"() {
        given:
        dataGenerator.generateSampleData()

        when:
        def doctors = visitRestApi.queryDoctors(queryFrom, queryTo)
        def doctorId = findDoctorId(doctors, queriedDoctorName[0], queriedDoctorName[1])
        def visitId = visitRestApi.queryVisits(queryFrom, queryTo, doctorId)[0].id
        def bookVisitRequest = prepareSamplePatientRequest()
        visitRestApi.bookVisitCommand(visitId, bookVisitRequest)

        then:
        def visit = visitRepository.findVisit(visitId).get()
        def patient = patientRepository.findById(visit.getPatientId()).get()
        assertPatientData(patient, bookVisitRequest.getPatient())
        visit.patientRemarks == bookVisitRequest.remarks
        visitRestApi.queryDoctors(queryFrom, queryTo).isEmpty()
        visitRestApi.queryVisits(queryFrom, queryTo, doctorId).isEmpty()

        where:
        queryFrom          | queryTo            | queriedDoctorName   || _
        "2022-02-05T13:00" | "2022-02-05T14:00" | ["Grazyna", "Macz"] || _
    }

    private BookVisitDto prepareSamplePatientRequest() {
        BookVisitDto.builder()
                .patient(PatientDto.builder()
                        .firstName("Ksionze")
                        .lastName("Kebaba")
                        .peselNumber("12345678910")
                        .build())
                .remarks("Nothing to add")
                .build()
    }

    void assertPatientData(Patient patient, PatientDto patientDto) {
        assert patient.firstName == patientDto.firstName
        assert patient.lastName == patientDto.lastName
        assert patient.peselNumber == patientDto.peselNumber
    }
}
