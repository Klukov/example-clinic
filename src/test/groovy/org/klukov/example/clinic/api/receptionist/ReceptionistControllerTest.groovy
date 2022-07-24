package org.klukov.example.clinic.api.receptionist

import groovy.util.logging.Slf4j
import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.domain.visit.VisitStatus
import org.klukov.example.clinic.repository.visit.VisitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class ReceptionistControllerTest extends Specification {

    @Autowired
    DataGenerator dataGenerator

    @Autowired
    ReceptionistRestApi receptionistRestApi

    @Autowired
    VisitRepository visitRepository

    @Autowired
    MockMvc mockMvc

    def cleanup() {
        dataGenerator.cleanup()
    }

    def "should confirm a visit"() {
        given:
        def data = dataGenerator.generateSampleData()
        def visit = data.visitsById.values()[4]


        when:
        def call = receptionistRestApi.confirmVisit(visit.id)

        then:
        call.andExpect(status().isOk())
        def updatedVisit = visitRepository.findVisit(visit.id).get()
        updatedVisit == visit.toBuilder().status(VisitStatus.CONFIRMED).build()
    }

    def "should exception be thrown if visit do not exists"() {
        given:
        def data = dataGenerator.generateSampleData()
        def visitId = data.visitsById.values().find { it.status == uncofirmableVisitStatus }.id

        when:
        def call = receptionistRestApi.confirmVisit(visitId)

        then:
        def response = call.andExpect(status().is5xxServerError())

        where:
        uncofirmableVisitStatus || _
        VisitStatus.FREE        || _
        VisitStatus.CONFIRMED   || _

    }

    def "should exception be thrown if visit is in wrong status"() {
        given:
        def data = dataGenerator.generateSampleData()
        def maxVisitId = data.visitsById.values().collect { it.id }.max()

        when:
        def call = receptionistRestApi.confirmVisit(maxVisitId + 1)

        then:
        def response = call.andExpect(status().is5xxServerError())

    }
}
