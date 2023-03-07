package org.klukov.example.clinic.api.doctor

import java.time.LocalDate
import java.time.LocalDateTime
import org.klukov.example.clinic.DataGenerator
import org.klukov.example.clinic.domain.visit.model.Visit
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
                .findAll { it.doctorId.value == doctor.id }
                .findAll { it.from.isAfter(getStartOfDayOfDate(from)) }
                .findAll { it.to.isBefore(getStartOfDayOfDate(to).plusDays(1)) }
                .sort { it.from }

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

    private void assertVisits(Collection<DoctorVisitDto> result, Collection<Visit> expected) {
        assert result.size() == expected.size()
        (0..(result.size() - 1)).each {
            assertVisit(result[it], expected[it])
        }
    }

    private void assertVisit(DoctorVisitDto result, Visit expected) {
        assert result.id == expected.id.value
        assert result.from == expected.from
        assert result.to == expected.to
        assert result.doctorId == expected.doctorId.value
        assert result.patientId == expected.patientId.map(e -> e.value).orElse(null)
        assert result.visitStatus == expected.status
    }

    LocalDateTime getStartOfDayOfDate(String s) {
        LocalDate.parse(s).atStartOfDay()
    }
}
