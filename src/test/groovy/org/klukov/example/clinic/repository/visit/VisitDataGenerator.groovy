package org.klukov.example.clinic.repository.visit

import org.klukov.example.clinic.domain.visit.Visit
import org.klukov.example.clinic.domain.visit.VisitStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDateTime

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
