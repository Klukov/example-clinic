package org.klukov.example.clinic.api.receptionist


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.domain.visit.model.VisitId
import org.klukov.example.clinic.domain.visit.model.VisitStatus
import org.klukov.example.clinic.domain.visit.out.VisitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@ActiveProfiles("test-containers")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
@AutoConfigureMockMvc
@Testcontainers
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
        def call = receptionistRestApi.confirmVisit(visit.id.value)

        then:
        call.andExpect(status().isOk())
        def updatedVisit = visitRepository.findVisit(VisitId.of(visit.id.value)).get()
        updatedVisit.status == VisitStatus.CONFIRMED
        updatedVisit == visit.toBuilder().status(VisitStatus.CONFIRMED).build()
    }

    def "should exception be thrown if visit do not exists"() {
        given:
        def data = dataGenerator.generateSampleData()
        def visitId = data.visitsById.values().find { it.status == uncofirmableVisitStatus }.id

        when:
        def call = receptionistRestApi.confirmVisit(visitId.value)

        then:
        thrown(Exception.class)
        //def response = call.andExpect(status().is5xxServerError()) // todo: fix this after spring error handling implementation

        where:
        uncofirmableVisitStatus || _
        VisitStatus.FREE        || _
        VisitStatus.CONFIRMED   || _
    }

    def "should exception be thrown if visit is in wrong status"() {
        given:
        def data = dataGenerator.generateSampleData()
        def maxVisitId = data.visitsById.values().collect { it.id.value }.max()

        when:
        def call = receptionistRestApi.confirmVisit(maxVisitId + 1)

        then:
        thrown(Exception.class)
        //def response = call.andExpect(status().is5xxServerError()) // todo: fix this after spring error handling implementation
    }
}
