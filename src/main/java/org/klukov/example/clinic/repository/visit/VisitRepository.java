package org.klukov.example.clinic.repository.visit;

import lombok.RequiredArgsConstructor;
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
public class VisitRepository {

    private final VisitJpaRepository visitJpaRepository;

    public List<Visit> findVisits(
            LocalDateTime from,
            LocalDateTime to,
            Long doctorId,
            Collection<VisitStatus> visitStatuses
    ) {
        return visitJpaRepository.findAllByDoctorIdAndTimeFromAfterAndTimeToBeforeAndStatusIn(
                        doctorId, from, to, visitStatuses).stream()
                .map(VisitModel::toDomain)
                .collect(Collectors.toList());
    }

    public List<Visit> findVisits(
            LocalDateTime from,
            LocalDateTime to,
            Collection<VisitStatus> visitStatuses
    ) {
        return visitJpaRepository.findAllByTimeFromAfterAndTimeToBeforeAndStatusIn(
                        from, to, visitStatuses).stream()
                .map(VisitModel::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<Visit> findVisit(Long id) {
        return visitJpaRepository.findById(id)
                .map(VisitModel::toDomain);
    }

    public Visit saveVisit(Visit visit) {
        return visitJpaRepository.save(VisitModel.fromDomain(visit)).toDomain();
    }

    public boolean visitExists(Long id) {
        return visitJpaRepository.existsById(id);
    }
}
