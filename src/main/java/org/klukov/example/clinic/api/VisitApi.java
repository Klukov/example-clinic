package org.klukov.example.clinic.api;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.dto.DoctorDto;
import org.klukov.example.clinic.api.dto.PatientDto;
import org.klukov.example.clinic.api.dto.SlotDto;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.domain.service.DoctorService;
import org.klukov.example.clinic.domain.service.VisitService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VisitApi {

    private final DoctorService doctorService;
    private final VisitService visitService;

    public List<DoctorDto> findAvailableDoctors(LocalDateTime from, LocalDateTime to) {
        return doctorService.findAllAvailableDoctors(from, to).stream()
                .map(DoctorDto::fromDomain)
                .collect(Collectors.toList());
    }

    public List<SlotDto> findAvailableSlots(LocalDateTime from, LocalDateTime to, Long doctorId) {
        return visitService.findVisits(from, to, doctorId, List.of(VisitStatus.FREE)).stream()
                .map(SlotDto::fromDomain)
                .collect(Collectors.toList());
    }

    public VisitDto bookVisit(Long visitId, PatientDto patientDto) {
        return VisitDto.fromDomain(visitService.bookVisit(visitId, patientDto.toDomain()));
    }
}