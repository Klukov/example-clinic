package org.klukov.example.clinic.api.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.api.dto.DoctorDto
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VisitControllerTest extends Specification {

    @Autowired
    DataGenerator dataGenerator

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    def cleanup() {
        dataGenerator.cleanup()
    }

    def "Should return only available doctors in specific time"() {
        given:
        dataGenerator.generateSampleData()

        when:
        def response = queryDoctors(queryFrom, queryTo)
        def result = getDoctorList(response)

        then:
        result.size() == expectedDoctors.size()
        (0..<expectedDoctors.size()).forEach(it ->
                assertDoctorDto(result[it], expectedDoctors[it][0], expectedDoctors[it][1])
        )

        where:
        queryFrom          | queryTo            || expectedDoctors
        "2019-05-01T23:10" | "2023-05-01T23:10" || [["Grazyna", "Macz"], ["Janusz", "Pracz"], ["Nova", "Super"]]
        "2022-02-03T11:10" | "2022-02-03T18:00" || [["Grazyna", "Macz"], ["Janusz", "Pracz"]]
        "2022-02-04T12:00" | "2022-02-04T16:00" || [["Janusz", "Pracz"], ["Nova", "Super"]]
    }

    private void assertDoctorDto(DoctorDto doctorDto, firstName, lastName) {
        assert doctorDto.id != null
        assert doctorDto.firstName == firstName
        assert doctorDto.lastName == lastName
    }

    private List<DoctorDto> getDoctorList(String response) {
        objectMapper.readValue(response, new TypeReference<List<DoctorDto>>() {})
    }

    private String queryDoctors(String from, String to) {
        mockMvc.perform(
                get("/public/v1/visit/doctors")
                        .param("from", from)
                        .param("to", to)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
    }

    def "GetAvailableSlots"() {

    }

    def "BookVisit"() {
    }
}
