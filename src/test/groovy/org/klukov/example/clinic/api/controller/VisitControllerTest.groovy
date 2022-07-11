package org.klukov.example.clinic.api.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.api.dto.BookVisitDto
import org.klukov.example.clinic.api.dto.DoctorDto
import org.klukov.example.clinic.api.dto.PatientDto
import org.klukov.example.clinic.api.dto.SlotDto
import org.klukov.example.clinic.domain.Patient
import org.klukov.example.clinic.repository.PatientRepository
import org.klukov.example.clinic.repository.VisitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

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
        def result = queryDoctors(queryFrom, queryTo)

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

    private List<DoctorDto> queryDoctors(String from, String to) {
        def mockResponse = mockMvc.perform(
                get("/public/v1/visit/doctors")
                        .param("from", from)
                        .param("to", to)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def result = objectMapper.readValue(mockResponse, new TypeReference<List<DoctorDto>>() {})
        log.info("DOCTORS QUERY RESULT WITH PARAMS: {}, {}, with result: {}", from, to, result)
        result
    }

    def "should return available visits for queried time and doctor"() {
        given:
        dataGenerator.generateSampleData()

        when:
        def doctors = queryDoctors(queryFrom, queryTo)
        def doctorId = findDoctorId(doctors, queriedDoctorName[0], queriedDoctorName[1])
        def result = queryVisits(queryFrom, queryTo, doctorId)

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

    private List<SlotDto> queryVisits(String from, String to, Long doctorId) {
        def mockResponse = mockMvc.perform(get("/public/v1/visit/available")
                .param("from", from)
                .param("to", to)
                .param("doctor", String.valueOf(doctorId))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def result = objectMapper.readValue(mockResponse, new TypeReference<List<SlotDto>>() {})
        log.info("FREE VISITS QUERY RESULT WITH PARAMS: {}, {}, with result: {}", from, to, result)
        result
    }

    def "should visit be booked, when patient register"() {
        given:
        dataGenerator.generateSampleData()

        when:
        def doctors = queryDoctors(queryFrom, queryTo)
        def doctorId = findDoctorId(doctors, queriedDoctorName[0], queriedDoctorName[1])
        def visitId = queryVisits(queryFrom, queryTo, doctorId)[0].id
        def bookVisitRequest = prepareSamplePatientRequest()
        bookVisitCommand(visitId, bookVisitRequest)

        then:
        def visit = visitRepository.findVisit(visitId).get()
        def patient = patientRepository.findById(visit.getPatientId()).get()
        assertPatientData(patient, bookVisitRequest.getPatient())
        visit.patientRemarks == bookVisitRequest.remarks
        queryDoctors(queryFrom, queryTo).isEmpty()
        queryVisits(queryFrom, queryTo, doctorId).isEmpty()

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

    private void bookVisitCommand(Long visitId, BookVisitDto bookVisitDto) {
        mockMvc.perform(post("/public/v1/visit/$visitId/book")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bookVisitDto)))
                .andExpect(status().isOk())
    }

    void assertPatientData(Patient patient, PatientDto patientDto) {
        assert patient.firstName == patientDto.firstName
        assert patient.lastName == patientDto.lastName
        assert patient.peselNumber == patientDto.peselNumber
    }
}
