package org.klukov.example.clinic.api;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.klukov.example.clinic.domain.Visit;
import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.domain.service.DoctorService;
import org.klukov.example.clinic.domain.service.VisitService;
import org.klukov.example.clinic.domain.utils.DateTimeUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorApi {

    private final DoctorService doctorService;
    private final VisitService visitService;

    @Transactional(readOnly = true)
    public List<VisitDto> findAllMyVisits(LocalDate from, LocalDate to, Long myId) {
        var visits = visitService.findVisits(
                from.atStartOfDay(),
                DateTimeUtils.atEndOfDay(to),
                myId,
                VisitStatus.all());
        return visits.stream()
                .sorted(Comparator.comparing(Visit::getFrom))
                .map(VisitDto::fromDomain)
                .collect(Collectors.toList());
    }
}
