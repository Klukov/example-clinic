package org.klukov.example.clinic.domain.visit.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class VisitId {
    long value;
}
