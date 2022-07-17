package org.klukov.example.clinic

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.klukov.example.clinic.api.dto.VisitDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Component
@Slf4j
class DoctorRestApi {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    List<VisitDto> queryDoctorVisits(String from, String to, Long doctorId) {
        def mockResponse = mockMvc.perform(
                get("/doctor/v1/my-visits")
                        .param("from", from)
                        .param("to", to)
                        .param("my-id", String.valueOf(doctorId))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def result = objectMapper.readValue(mockResponse, new TypeReference<List<VisitDto>>() {})
        log.info("FREE VISITS QUERY RESULT WITH PARAMS: {}, {}, with result: {}", from, to, result)
        result
    }
}
