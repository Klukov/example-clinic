package org.klukov.example.clinic.api.controller

import groovy.util.logging.Slf4j
import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.DoctorRestApi
import org.klukov.example.clinic.api.dto.VisitDto
import org.klukov.example.clinic.repository.dao.VisitDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class DoctorControllerTest extends Specification {

    @Autowired
    DataGenerator dataGenerator

    @Autowired
    DoctorRestApi doctorRestApi

    def cleanup() {
        dataGenerator.cleanup()
    }

    def "should return all doctor visits"() {
        given:
        def data = dataGenerator.generateSampleData()
        def doctor = data.doctorsByName[doctorName]
        def expectedVisits = data.visitsById.values()
                .findAll { it.doctorId == doctor.id }
                .findAll { it.timeFrom.isAfter(getStartOfDayOfDate(from)) }
                .findAll { it.timeTo.isBefore(getStartOfDayOfDate(to).plusDays(1)) }
                .sort { it.timeFrom }

        when:
        def result = doctorRestApi.queryDoctorVisits(from, to, doctor.id)

        then:
        assertVisits(result, expectedVisits)

        where:
        from         | to           | doctorName     || _
        "2019-05-01" | "2023-05-01" | "Janusz Pracz" || _
        "2019-05-01" | "2023-05-01" | "Grazyna Macz" || _
        "2022-02-05" | "2022-02-05" | "Grazyna Macz" || _
    }

    private void assertVisits(Collection<VisitDto> result, Collection<VisitDao> expected) {
        assert result.size() == expected.size()
        (0..(result.size() - 1)).each {
            assertVisit(result[it], expected[it])
        }
    }

    private void assertVisit(VisitDto result, VisitDao expected) {
        assert result.id == expected.id
        assert result.from == expected.timeFrom
        assert result.to == expected.timeTo
        assert result.doctorId == expected.doctorId
        assert result.patientId == expected.patientId
        assert result.visitStatus == expected.status
    }

    LocalDateTime getStartOfDayOfDate(String s) {
        LocalDate.parse(s).atStartOfDay()
    }
}
