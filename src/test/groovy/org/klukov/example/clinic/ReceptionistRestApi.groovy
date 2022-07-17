package org.klukov.example.clinic

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@Component
@Slf4j
class ReceptionistRestApi {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    ResultActions confirmVisit(Long visitId) {
        mockMvc.perform(
                post("/receptionist/v1/visit/$visitId/confirm")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
    }
}
