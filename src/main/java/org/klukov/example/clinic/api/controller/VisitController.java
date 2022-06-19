package org.klukov.example.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.VisitApi;
import org.klukov.example.clinic.api.dto.DoctorDto;
import org.klukov.example.clinic.api.dto.PatientDto;
import org.klukov.example.clinic.api.dto.SlotDto;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("public/v1/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitApi visitApi;

    @GetMapping("doctors")
    public List<DoctorDto> getAvailableDoctors(
            @RequestParam("from") @DateTimeFormat(iso = DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DATE_TIME) LocalDateTime to
    ) {
        return visitApi.findAvailableDoctors(from, to);
    }

    @GetMapping("available")
    public List<SlotDto> getAvailableSlots(
            @RequestParam("from") @DateTimeFormat(iso = DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DATE_TIME) LocalDateTime to,
            @RequestParam("doctor") Long doctorId
    ) {
        return visitApi.findAvailableSlots(from, to, doctorId);
    }

    @PostMapping("{id}/book")
    public VisitDto bookVisit(
            @PathVariable("id") Long visitId,
            @Validated @RequestBody PatientDto patientDto
    ) {
        return visitApi.bookVisit(visitId, patientDto);
    }

}
