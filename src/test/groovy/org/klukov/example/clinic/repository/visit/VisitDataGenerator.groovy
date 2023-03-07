package org.klukov.example.clinic.repository.visit

import java.time.LocalDateTime
import org.klukov.example.clinic.domain.visit.model.Visit
import org.klukov.example.clinic.domain.visit.model.VisitStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VisitDataGenerator {

    @Autowired
    VisitJpaRepository visitJpaRepository

    def cleanup() {
        visitJpaRepository.deleteAll()
    }

    Visit generateVisit(
            LocalDateTime from,
            Long doctorId,
            VisitStatus visitStatus = VisitStatus.FREE,
            Long patientId = null
    ) {
        visitJpaRepository.save(
                new VisitDao(
                        doctorId: doctorId,
                        patientId: patientId,
                        timeFrom: from,
                        timeTo: from.plusHours(1),
                        status: visitStatus,
                )
        ).toDomain()
    }
}
