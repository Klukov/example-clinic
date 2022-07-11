package org.klukov.example.clinic

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.klukov.example.clinic.api.dto.BookVisitDto
import org.klukov.example.clinic.api.dto.DoctorDto
import org.klukov.example.clinic.api.dto.SlotDto
import org.klukov.example.clinic.domain.DoctorSpecialization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Component
@Slf4j
class VisitRestApi {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    void bookVisitCommand(Long visitId, BookVisitDto bookVisitDto) {
        mockMvc.perform(
                post("/public/v1/visit/$visitId/book")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(bookVisitDto)))
                .andExpect(status().isOk())
    }

    List<DoctorDto> queryDoctors(
            String from,
            String to,
            DoctorSpecialization doctorSpecialization = DoctorSpecialization.DENTIST
    ) {
        def mockResponse = mockMvc.perform(
                get("/public/v1/visit/doctors")
                        .param("from", from)
                        .param("to", to)
                        .param("specialization", doctorSpecialization.toString())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def result = objectMapper.readValue(mockResponse, new TypeReference<List<DoctorDto>>() {})
        log.info("DOCTORS QUERY RESULT WITH PARAMS: {}, {}, with result: {}", from, to, result)
        result
    }

    List<SlotDto> queryVisits(String from, String to, Long doctorId) {
        def mockResponse = mockMvc.perform(
                get("/public/v1/visit/available")
                        .param("from", from)
                        .param("to", to)
                        .param("doctor", String.valueOf(doctorId))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def result = objectMapper.readValue(mockResponse, new TypeReference<List<SlotDto>>() {})
        log.info("FREE VISITS QUERY RESULT WITH PARAMS: {}, {}, with result: {}", from, to, result)
        result
    }
}
