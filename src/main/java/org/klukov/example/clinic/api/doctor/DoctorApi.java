package org.klukov.example.clinic.api.doctor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitQuery;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitsUseCase;
import org.klukov.example.clinic.domain.visit.model.Visit;
import org.klukov.example.clinic.domain.visit.model.VisitStatus;
import org.klukov.example.clinic.utils.DateTimeUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class DoctorApi {

    private final AvailableVisitsUseCase availableVisitsUseCase;

    @Transactional(readOnly = true)
    public List<DoctorVisitDto> findAllMyVisits(LocalDate from, LocalDate to, long myId) {
        var visits =
                availableVisitsUseCase.findVisits(
                        AvailableVisitQuery.builder()
                                .from(from.atStartOfDay())
                                .to(DateTimeUtils.atEndOfDay(to))
                                .doctorId(DoctorId.of(myId))
                                .statuses(VisitStatus.all())
                                .build());
        return visits.stream()
                .sorted(Comparator.comparing(Visit::getFrom))
                .map(DoctorVisitDto::fromDomain)
                .collect(Collectors.toList());
    }
}
