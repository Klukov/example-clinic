package org.klukov.example.clinic.repository.visit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class VisitRepository {

    private final VisitJpaRepository visitJpaRepository;

    public List<Visit> findVisits(
            LocalDateTime from,
            LocalDateTime to,
            Long doctorId,
            Collection<VisitStatus> visitStatuses
    ) {
        var result = visitJpaRepository.findAllVisits(
                        from, to, visitStatuses, doctorId).stream()
                .map(VisitDao::toDomain)
                .collect(Collectors.toList());
        log.debug("Visits query to db: {}, {}, {}, {}, with result: {}", from, to, doctorId, visitStatuses, result);
        return result;
    }

    public List<Visit> findVisits(
            LocalDateTime from,
            LocalDateTime to,
            Collection<VisitStatus> visitStatuses
    ) {
        var result = visitJpaRepository.findAllVisits(
                        from, to, visitStatuses, null).stream()
                .map(VisitDao::toDomain)
                .collect(Collectors.toList());
        log.debug("Visits query to db: {}, {}, {}, with result: {}", from, to, visitStatuses, result);
        return result;
    }

    public Optional<Visit> findVisit(Long id) {
        return visitJpaRepository.findById(id)
                .map(VisitDao::toDomain);
    }

    public Visit saveVisit(Visit visit) {
        return visitJpaRepository.save(VisitDao.fromDomain(visit)).toDomain();
    }
}
