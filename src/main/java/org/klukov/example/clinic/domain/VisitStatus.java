package org.klukov.example.clinic.domain;

import java.util.Set;

public enum VisitStatus {
    FREE,
    OCCUPIED,
    CONFIRMED;

    public static Set<VisitStatus> all() {
        return Set.of(VisitStatus.values());
    }
}
