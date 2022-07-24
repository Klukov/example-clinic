package org.klukov.example.clinic.api.doctor;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitService;
import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.klukov.example.clinic.utils.DateTimeUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DoctorApi {

    private final VisitService visitService;

    @Transactional(readOnly = true)
    public List<DoctorVisitDto> findAllMyVisits(LocalDate from, LocalDate to, Long myId) {
        var visits = visitService.findVisits(
                from.atStartOfDay(),
                DateTimeUtils.atEndOfDay(to),
                myId,
                VisitStatus.all());
        return visits.stream()
                .sorted(Comparator.comparing(Visit::getFrom))
                .map(DoctorVisitDto::fromDomain)
                .collect(Collectors.toList());
    }
}
