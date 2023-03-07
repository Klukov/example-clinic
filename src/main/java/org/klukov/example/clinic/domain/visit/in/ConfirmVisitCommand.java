package org.klukov.example.clinic.domain.visit.in;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.klukov.example.clinic.domain.visit.model.VisitId;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmVisitCommand {
    VisitId visitId;
    Long receptionistId;
}
