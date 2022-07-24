package org.klukov.example.clinic.api.patient;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.Doctor;
import org.klukov.example.clinic.domain.doctor.DoctorService;
import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;
import org.klukov.example.clinic.domain.visit.BookVisitCommand;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitService;
import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class PatientPublicApi {

    private final DoctorService doctorService;
    private final VisitService visitService;

    @Transactional(readOnly = true)
    public List<DoctorDto> findAvailableDoctors(LocalDateTime from, LocalDateTime to, DoctorSpecialization doctorSpecialization) {
        return doctorService.findAllAvailableDoctors(from, to, doctorSpecialization).stream()
                .sorted(Comparator.comparing(Doctor::getRating, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(DoctorDto::fromDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SlotDto> findAvailableSlots(LocalDateTime from, LocalDateTime to, Long doctorId) {
        return visitService.findVisits(from, to, doctorId, List.of(VisitStatus.FREE)).stream()
                .sorted(Comparator.comparing(Visit::getFrom))
                .map(SlotDto::fromDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientVisitDto bookVisit(Long visitId, BookVisitDto bookVisitDto) {
        var bookVisitCommand = BookVisitCommand.builder()
                .visitId(visitId)
                .patient(bookVisitDto.getPatient().toDomain())
                .patientRemarks(bookVisitDto.getRemarks())
                .build();
        return PatientVisitDto.fromDomain(visitService.bookVisit(bookVisitCommand));
    }
}