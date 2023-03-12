package org.klukov.example.clinic.api.patient

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.domain.visit.model.Patient
import org.klukov.example.clinic.domain.visit.model.VisitId
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

    def "should visit be booked, when patient send register request"() {
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

    def "should throw exception when payload for visit booking is invalid, field #invalidField"() {
        given:
        def queryFrom = "2022-02-05T13:00"
        def queryTo = "2022-02-05T14:00"
        def doctors = visitRestApi.queryDoctors(queryFrom, queryTo)
        def doctorId = findDoctorId(doctors, "Grazyna", "Macz")
        def visitId = visitRestApi.queryVisits(queryFrom, queryTo, doctorId)[0].id
        def bookVisitRequest = generateBookVisitRequest(firstName, lastName, peselNumber, phone)

        when:
        def result = visitRestApi.callBookVisitCommand(visitId, bookVisitRequest)

        then:
        result.andExpect(status().isBadRequest())

        where:
        invalidField  | firstName | lastName | peselNumber    | phone        || _
        "firstName"   | null      | "Last"   | "12345678910"  | "123456789"  || _
        "lastName"    | "First"   | null     | "12345678910"  | "123456789"  || _
        "peselNumber" | "First"   | "Last"   | null           | "123456789"  || _
        "peselNumber" | "First"   | "Last"   | "1234567891"   | "123456789"  || _ // too short
        "peselNumber" | "First"   | "Last"   | "123456789101" | "123456789"  || _ // too long
        "phone"       | "First"   | "Last"   | "12345678910"  | null         || _
        "phone"       | "First"   | "Last"   | "12345678910"  | "12345678"   || _ // to short
        "phone"       | "First"   | "Last"   | "12345678910"  | "1234567891" || _ // to long
    }

    private BookVisitDto generateBookVisitRequest(String firstName, String lastName, String peselNumber, String phone) {
        BookVisitDto.builder()
                .patient(PatientDto.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .peselNumber(peselNumber)
                        .phone(phone)
                        .build())
                .remarks("Nothing to add")
                .build()
    }

    private BookVisitDto prepareSamplePatientRequest() {
        generateBookVisitRequest("Ksionze", "Kebaba", "12345678910", "123456789")
    }

    void assertPatientData(Patient patient, PatientDto patientDto) {
        assert patient.firstName == patientDto.firstName
        assert patient.lastName == patientDto.lastName
        assert patient.peselNumber == patientDto.peselNumber
        assert patient.phone == patientDto.phone
    }
}
