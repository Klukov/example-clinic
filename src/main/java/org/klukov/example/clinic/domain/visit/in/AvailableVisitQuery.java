package org.klukov.example.clinic.domain.visit.in;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.visit.model.VisitStatus;

@Value
@Builder
public class AvailableVisitQuery {
    @NonNull LocalDateTime from;
    @NonNull LocalDateTime to;

    @Getter(AccessLevel.NONE)
    @NonNull Set<VisitStatus> statuses;

    DoctorId doctorId;

    public Set<VisitStatus> getStatuses() {
        return Set.copyOf(statuses);
    }
}
