package org.klukov.example.clinic.api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class VisitControllerTest extends Specification {

    @Autowired
    DataGenerator dataGenerator


    def "GetAvailableDoctors"() {
        given:
        dataGenerator.generateSampleData()

        when:

        then:

    }

    def "GetAvailableSlots"() {
    }

    def "BookVisit"() {
    }
}
