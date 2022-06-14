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

    def "GetAvailableDoctors"() {
        given:
        dataGenerator.generateSampleData()

        when:
        def response = mockMvc.perform(
                get("/public/v1/visit/doctors")
                        .param("from", "2019-05-01T23:10")
                        .param("to", "2023-05-01T23:10")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def result = objectMapper.readValue(response, new TypeReference<List<DoctorDto>>() {})

        then:
        result.size() == 2
        result[0].firstName == "Janusz"
        result[0].lastName == "Pracz"
        result[1].firstName == "Grazyna"
        result[1].lastName == "Macz"

    }

    def "GetAvailableSlots"() {

    }

    def "BookVisit"() {
    }
}
