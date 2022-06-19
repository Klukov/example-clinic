package org.klukov.example.clinic.domain.visit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Visit {
    Long id;
    LocalDateTime from;
    LocalDateTime to;
    Long doctorId;
    Long patientId;
    VisitStatus visitStatus;
}
