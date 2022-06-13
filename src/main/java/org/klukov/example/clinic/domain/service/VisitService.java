package org.klukov.example.clinic.domain.service;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.Patient;
import org.klukov.example.clinic.domain.Visit;
import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.repository.PatientRepository;
import org.klukov.example.clinic.repository.VisitRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;

    public List<Visit> findVisits(
            LocalDateTime from,
            LocalDateTime to,
            Long doctorId,
            Collection<VisitStatus> statuses
    ) {
        return visitRepository.findVisits(from, to, doctorId, statuses);
    }

    public Visit bookVisit(Long visitId, Patient patient) {
        var visitBuilder = visitRepository.findVisit(visitId)
                .filter(visit -> visit.getVisitStatus() == VisitStatus.FREE)
                .map(Visit::toBuilder)
                .orElseThrow(() -> new RuntimeException("Visit do not exists or is in the wrong status"));
        var createdPatient = patientRepository.save(patient);
        var newVisit = visitBuilder
                .visitStatus(VisitStatus.OCCUPIED)
                .patientId(createdPatient.getId())
                .build();
        return visitRepository.saveVisit(newVisit);
    }
}
