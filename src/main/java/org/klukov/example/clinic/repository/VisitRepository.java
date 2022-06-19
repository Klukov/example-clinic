package org.klukov.example.clinic.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.klukov.example.clinic.domain.Visit;
import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.repository.dao.VisitDao;
import org.klukov.example.clinic.repository.db.VisitJpaRepository;
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
        log.info("Visits query to db: {}, {}, {}, {}", from, to, doctorId, visitStatuses);
        return visitJpaRepository.findAllByDoctorIdAndTimeFromAfterAndTimeToBeforeAndStatusIn(
                        doctorId, from, to, visitStatuses).stream()
                .map(VisitDao::toDomain)
                .collect(Collectors.toList());
    }

    public List<Visit> findVisits(
            LocalDateTime from,
            LocalDateTime to,
            Collection<VisitStatus> visitStatuses
    ) {
        log.info("Visits query to db: {}, {}, {}", from, to, visitStatuses);
        return visitJpaRepository.findAllByTimeFromAfterAndTimeToBeforeAndStatusIn(
                        from, to, visitStatuses).stream()
                .map(VisitDao::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<Visit> findVisit(Long id) {
        return visitJpaRepository.findById(id)
                .map(VisitDao::toDomain);
    }

    public Visit saveVisit(Visit visit) {
        return visitJpaRepository.save(VisitDao.fromDomain(visit)).toDomain();
    }

    public boolean visitExists(Long id) {
        return visitJpaRepository.existsById(id);
    }
}
