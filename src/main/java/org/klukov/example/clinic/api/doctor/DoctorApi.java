package org.klukov.example.clinic.api.doctor;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitService;
import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DoctorApi {

    private final VisitService visitService;

    @Transactional
    VisitDto confirmVisit(Long doctorId, ConfirmVisitDto confirmVisitDto) {
        var confirmedVisit = visitService.confirmVisit(doctorId, confirmVisitDto.getVisitId());
        return VisitDto.fromDomain(confirmedVisit);
    }

    @Transactional(readOnly = true)
    List<VisitDto> findAllMyVisits(LocalDate from, LocalDate to, Long myId) {
        var visits = visitService.findVisits(
                map(from, false),
                map(to, true),
                myId,
                VisitStatus.all());
        return visits.stream()
                .sorted(Comparator.comparing(Visit::getFrom))
                .map(VisitDto::fromDomain)
                .collect(Collectors.toList());
    }

    private LocalDateTime map(LocalDate localDate, boolean isEndOfDay) {
        if (isEndOfDay) {
            return localDate.plusDays(1).atStartOfDay();
        } else {
            return localDate.atStartOfDay();
        }
    }
}
