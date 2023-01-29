package org.klukov.example.clinic.api.patient

import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.domain.visit.Patient
import org.klukov.example.clinic.domain.visit.VisitId
import org.klukov.example.clinic.domain.visit.out.PatientRepository
import org.klukov.example.clinic.domain.visit.out.VisitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@ActiveProfiles("test-containers")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
@AutoConfigureMockMvc
@Testcontainers
class PatientPublicControllerTest extends Specification {

    @Autowired
    DataGenerator dataGenerator

    @Autowired
    PatientRestApi visitRestApi

    @Autowired
    VisitRepository visitRepository

    @Autowired
    PatientRepository patientRepository

    def cleanup() {
        dataGenerator.cleanup()
    }

    def setup() {
        dataGenerator.generateSampleData()
    }

    def "Should return only available doctors in specific time"() {
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
        when:
        def doctors = visitRestApi.queryDoctors(queryFrom, queryTo)
        def doctorId = findDoctorId(doctors, queriedDoctorName[0], queriedDoctorName[1])
        def visitId = visitRestApi.queryVisits(queryFrom, queryTo, doctorId)[0].id
        def bookVisitRequest = prepareSamplePatientRequest()
        visitRestApi.bookVisitCommand(visitId, bookVisitRequest)

        then:
        def visit = visitRepository.findVisit(VisitId.of(visitId)).get()
        def patient = patientRepository.findById(visit.getPatientId().get()).get()
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
