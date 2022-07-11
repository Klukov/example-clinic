package org.klukov.example.clinic.api;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.dto.BookVisitDto;
import org.klukov.example.clinic.api.dto.DoctorDto;
import org.klukov.example.clinic.api.dto.SlotDto;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.klukov.example.clinic.domain.BookVisitCommand;
import org.klukov.example.clinic.domain.Doctor;
import org.klukov.example.clinic.domain.Visit;
import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.domain.service.DoctorService;
import org.klukov.example.clinic.domain.service.VisitService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VisitApi {

    private final DoctorService doctorService;
    private final VisitService visitService;

    @Transactional(readOnly = true)
    public List<DoctorDto> findAvailableDoctors(LocalDateTime from, LocalDateTime to) {
        return doctorService.findAllAvailableDoctors(from, to).stream()
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
    public VisitDto bookVisit(Long visitId, BookVisitDto bookVisitDto) {
        var bookVisitCommand = BookVisitCommand.builder()
                .visitId(visitId)
                .patient(bookVisitDto.getPatient().toDomain())
                .patientRemarks(bookVisitDto.getRemarks())
                .build();
        return VisitDto.fromDomain(visitService.bookVisit(bookVisitCommand));
    }
}