package org.klukov.example.clinic.repository.visit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.doctor.out.DoctorAvailabilityRepository;
import org.klukov.example.clinic.domain.exception.ClinicRuntimeException;
import org.klukov.example.clinic.domain.visit.model.PatientId;
import org.klukov.example.clinic.domain.visit.model.Visit;
import org.klukov.example.clinic.domain.visit.model.VisitId;
import org.klukov.example.clinic.domain.visit.model.VisitStatus;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitQuery;
import org.klukov.example.clinic.domain.visit.out.VisitRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class VisitRepositoryImpl implements VisitRepository, DoctorAvailabilityRepository {

    private final VisitJpaRepository visitJpaRepository;

    @Override
    public List<Visit> findVisits(AvailableVisitQuery availableVisitQuery) {
        var result = visitJpaRepository.findAllVisits(
                        availableVisitQuery.getFrom(),
                        availableVisitQuery.getTo(),
                        availableVisitQuery.getStatuses(),
                        availableVisitQuery.getDoctorId().getValue())
                .stream()
                .map(VisitDao::toDomain)
                .collect(Collectors.toList());
        log.debug("Visits query to db: {}, with result: {}", availableVisitQuery, result);
        return result;
    }

    @Override
    public Optional<Visit> findVisit(VisitId visitId) {
        return visitJpaRepository.findById(visitId.getValue())
                .map(VisitDao::toDomain);
    }

    @Override
    public Visit update(Visit visit) {
        var existingVisit = visitJpaRepository.findById(visit.getId().getValue())
                .map(VisitDao::toBuilder)
                .orElseThrow(() -> new ClinicRuntimeException(String.format("Visit with id %s do not exists", visit.getId())))
                .timeFrom(visit.getFrom())
                .timeTo(visit.getTo())
                .doctorId(visit.getDoctorId().getValue())
                .status(visit.getStatus())
                .patientId(visit.getPatientId().map(PatientId::getValue).orElse(null))
                .patientRemarks(visit.getPatientRemarks())
                .build();
        return visitJpaRepository.save(existingVisit).toDomain();
    }

    @Override
    public Set<DoctorId> findAllAvailableDoctors(LocalDateTime from, LocalDateTime to) {
        return visitJpaRepository.findAllVisits(
                        from, to, List.of(VisitStatus.FREE), null).stream()
                .map(VisitDao::getDoctorId)
                .map(DoctorId::of)
                .collect(Collectors.toSet());
    }
}
