package org.klukov.example.clinic.domain.visit;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.exception.ClinicRuntimeException;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitQuery;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitsUseCase;
import org.klukov.example.clinic.domain.visit.in.BookVisitCommand;
import org.klukov.example.clinic.domain.visit.in.BookVisitUseCase;
import org.klukov.example.clinic.domain.visit.in.ConfirmVisitCommand;
import org.klukov.example.clinic.domain.visit.in.ConfirmVisitUseCase;
import org.klukov.example.clinic.domain.visit.model.NewPatient;
import org.klukov.example.clinic.domain.visit.model.Visit;
import org.klukov.example.clinic.domain.visit.model.VisitStatus;
import org.klukov.example.clinic.domain.visit.out.PatientRepository;
import org.klukov.example.clinic.domain.visit.out.VisitRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class VisitService implements BookVisitUseCase, ConfirmVisitUseCase, AvailableVisitsUseCase {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;

    @Override
    public List<Visit> findVisits(AvailableVisitQuery availableVisitQuery) {
        return visitRepository.findVisits(availableVisitQuery);
    }

    @Override
    public Visit bookVisit(BookVisitCommand bookVisitCommand) {
        var visitBuilder = visitRepository.findVisit(bookVisitCommand.getVisitId())
                .filter(visit -> VisitStatus.FREE.equals(visit.getStatus()))
                .map(Visit::toBuilder)
                .orElseThrow(() -> new ClinicRuntimeException("Visit do not exists or is in the wrong status"));
        var createdPatient = patientRepository.save(
                NewPatient.builder()
                        .firstName(bookVisitCommand.getPatient().getFirstName())
                        .lastName(bookVisitCommand.getPatient().getLastName())
                        .peselNumber(bookVisitCommand.getPatient().getPeselNumber())
                        .build());
        var newVisit = visitBuilder
                .status(VisitStatus.OCCUPIED)
                .patientId(createdPatient.getId())
                .patientRemarks(bookVisitCommand.getPatientRemarks())
                .build();
        return visitRepository.update(newVisit);
    }

    @Override
    public Visit confirmVisit(ConfirmVisitCommand confirmVisitCommand) {
        var visit = visitRepository.findVisit(confirmVisitCommand.getVisitId())
                .filter(v -> VisitStatus.OCCUPIED.equals(v.getStatus()))
                .map(Visit::toBuilder)
                .orElseThrow(() -> new ClinicRuntimeException("visit do not exists or is in the wrong state"))
                .status(VisitStatus.CONFIRMED);
        return visitRepository.update(visit.build());
    }
}
