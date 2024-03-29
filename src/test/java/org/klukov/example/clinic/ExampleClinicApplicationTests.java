package org.klukov.example.clinic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test-containers")
@AutoConfigureMockMvc
class ExampleClinicApplicationTests {

    @Test
    void contextLoads() {}
}
