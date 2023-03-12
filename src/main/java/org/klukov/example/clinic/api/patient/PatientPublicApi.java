package org.klukov.example.clinic.api.patient;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.in.AvailableDoctorsQuery;
import org.klukov.example.clinic.domain.doctor.in.AvailableDoctorsUseCase;
import org.klukov.example.clinic.domain.doctor.model.Doctor;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitQuery;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitsUseCase;
import org.klukov.example.clinic.domain.visit.in.BookVisitCommand;
import org.klukov.example.clinic.domain.visit.in.BookVisitUseCase;
import org.klukov.example.clinic.domain.visit.model.Visit;
import org.klukov.example.clinic.domain.visit.model.VisitId;
import org.klukov.example.clinic.domain.visit.model.VisitStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class PatientPublicApi {

    private final AvailableDoctorsUseCase availableDoctorsUseCase;
    private final BookVisitUseCase bookVisitUseCase;
    private final AvailableVisitsUseCase availableVisitsUseCase;

    @Transactional(readOnly = true)
    public List<DoctorDto> findAvailableDoctors(
            LocalDateTime from, LocalDateTime to, DoctorSpecialization doctorSpecialization) {
        var availableDoctorsQuery =
                AvailableDoctorsQuery.builder()
                        .from(from)
                        .to(to)
                        .doctorSpecializations(Set.of(doctorSpecialization))
                        .build();
        return availableDoctorsUseCase.findAll(availableDoctorsQuery).stream()
                .sorted(
                        Comparator.comparing(
                                Doctor::getRating, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(DoctorDto::fromDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SlotDto> findAvailableSlots(LocalDateTime from, LocalDateTime to, Long doctorId) {
        return availableVisitsUseCase
                .findVisits(
                        AvailableVisitQuery.builder()
                                .from(from)
                                .to(to)
                                .doctorId(DoctorId.of(doctorId))
                                .statuses(Set.of(VisitStatus.FREE))
                                .build())
                .stream()
                .sorted(Comparator.comparing(Visit::getFrom))
                .map(SlotDto::fromDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientVisitDto bookVisit(Long visitId, BookVisitDto bookVisitDto) {
        var bookVisitCommand =
                BookVisitCommand.builder()
                        .visitId(VisitId.of(visitId))
                        .patient(buildPatient(bookVisitDto.getPatient()))
                        .patientRemarks(bookVisitDto.getRemarks())
                        .build();
        return PatientVisitDto.fromDomain(bookVisitUseCase.bookVisit(bookVisitCommand));
    }

    private BookVisitCommand.Patient buildPatient(PatientDto patientDto) {
        return BookVisitCommand.Patient.builder()
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .peselNumber(patientDto.getPeselNumber())
                .phone(patientDto.getPhone())
                .build();
    }
}
