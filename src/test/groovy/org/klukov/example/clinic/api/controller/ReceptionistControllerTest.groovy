package org.klukov.example.clinic.api.controller

import groovy.util.logging.Slf4j
import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.ReceptionistRestApi
import org.klukov.example.clinic.domain.VisitStatus
import org.klukov.example.clinic.repository.VisitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
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
        updatedVisit == visit.toBuilder().visitStatus(VisitStatus.CONFIRMED).build()
    }

    def "should exception be thrown if visit do not exists"() {
        given:
        def data = dataGenerator.generateSampleData()
        def visit = data.visitsById.values().find { it.visitStatus == uncofirmableVisitStatus }


        when:
        def call = receptionistRestApi.confirmVisit(visit.id)

        then:
        def response = call.andExpect(status().is4xxClientError())

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
        def response = call.andExpect(status().is4xxClientError())
    }
}
