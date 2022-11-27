package org.klukov.example.clinic.api.patient;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.Doctor;
import org.klukov.example.clinic.domain.doctor.DoctorId;
import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;
import org.klukov.example.clinic.domain.doctor.in.AvailableDoctorsQuery;
import org.klukov.example.clinic.domain.doctor.in.AvailableDoctorsUseCase;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitId;
import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitQuery;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitsUseCase;
import org.klukov.example.clinic.domain.visit.in.BookVisitCommand;
import org.klukov.example.clinic.domain.visit.in.BookVisitUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PatientPublicApi {

    private final AvailableDoctorsUseCase availableDoctorsUseCase;
    private final BookVisitUseCase bookVisitUseCase;
    private final AvailableVisitsUseCase availableVisitsUseCase;

    @Transactional(readOnly = true)
    public List<DoctorDto> findAvailableDoctors(LocalDateTime from, LocalDateTime to, DoctorSpecialization doctorSpecialization) {
        var availableDoctorsQuery = AvailableDoctorsQuery.builder()
                .from(from)
                .to(to)
                .doctorSpecializations(Set.of(doctorSpecialization))
                .build();
        return availableDoctorsUseCase.findAll(availableDoctorsQuery).stream()
                .sorted(Comparator.comparing(Doctor::getRating, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(DoctorDto::fromDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SlotDto> findAvailableSlots(LocalDateTime from, LocalDateTime to, Long doctorId) {
        return availableVisitsUseCase.findVisits(
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
        var bookVisitCommand = BookVisitCommand.builder()
                .visitId(VisitId.of(visitId))
                .patient(BookVisitCommand.Patient.builder()
                        .firstName(bookVisitDto.getPatient().getFirstName())
                        .lastName(bookVisitDto.getPatient().getLastName())
                        .peselNumber(bookVisitDto.getPatient().getPeselNumber())
                        .build())
                .patientRemarks(bookVisitDto.getRemarks())
                .build();
        return PatientVisitDto.fromDomain(bookVisitUseCase.bookVisit(bookVisitCommand));
    }
}