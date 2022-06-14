package org.klukov.example.clinic.api;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.dto.ConfirmVisitDto;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.domain.service.DoctorService;
import org.klukov.example.clinic.domain.service.VisitService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorApi {

    private final DoctorService doctorService;
    private final VisitService visitService;

    public VisitDto confirmVisit(Long doctorId, ConfirmVisitDto confirmVisitDto) {
        var confirmedVisit = doctorService.confirmVisit(doctorId, confirmVisitDto.getVisitId());
        return VisitDto.fromDomain(confirmedVisit);
    }

    @Transactional
    public List<VisitDto> findAllMyVisits(LocalDate from, LocalDate to, Long myId) {
        var visits = visitService.findVisits(
                map(from, false),
                map(to, true),
                myId,
                VisitStatus.all());
        return visits.stream()
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
