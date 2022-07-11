package org.klukov.example.clinic.domain.service;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.BookVisitCommand;
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

    public Visit bookVisit(BookVisitCommand bookVisitCommand) {
        var visitBuilder = visitRepository.findVisit(bookVisitCommand.getVisitId())
                .filter(visit -> VisitStatus.FREE.equals(visit.getVisitStatus()))
                .map(Visit::toBuilder)
                .orElseThrow(() -> new RuntimeException("Visit do not exists or is in the wrong status"));
        var createdPatient = patientRepository.save(bookVisitCommand.getPatient());
        var newVisit = visitBuilder
                .visitStatus(VisitStatus.OCCUPIED)
                .patientId(createdPatient.getId())
                .patientRemarks(bookVisitCommand.getPatientRemarks())
                .build();
        return visitRepository.saveVisit(newVisit);
    }
}
