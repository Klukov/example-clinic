package org.klukov.example.clinic.domain.visit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookVisitCommand {
    long visitId;
    Patient patient;
    String patientRemarks;
}
