package org.klukov.example.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.DoctorApi;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("doctor/v1")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorApi doctorApi;

    @GetMapping("my-visits")
    public List<VisitDto> getMyVisits(
            @RequestParam("from") @DateTimeFormat(iso = DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DATE) LocalDate to,
            @RequestParam("my-id") Long myId //todo: use authorization to retrieve doctor id
    ) {
        return doctorApi.findAllMyVisits(from, to, myId);
    }
}
