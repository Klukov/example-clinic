package org.klukov.example.clinic.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.klukov.example.clinic.DataGenerator
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
        //dataGenerator.generateSampleData()

        when:
        def result = mockMvc.perform(get("/public/v1/visit/doctors")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())

        then:
        result != null
    }

//    def "GetAvailableSlots"() {
//
//    }
//
//    def "BookVisit"() {
//    }
}
