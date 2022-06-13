package org.klukov.example.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.DoctorApi;
import org.klukov.example.clinic.api.dto.ConfirmVisitDto;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("doctor/v1")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorApi doctorApi;

    @GetMapping("my-visits")
    public List<VisitDto> getMyVisits(
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to,
            @RequestParam("my-id") Long myId
    ) {
        //todo: use authorization to retrieve doctor id
        return doctorApi.findAllMyVisits(from, to, myId);
    }

    @PostMapping("visit/confirm")
    public VisitDto confirmVisit(
            @Validated @RequestBody ConfirmVisitDto confirmVisitDto,
            @RequestParam Long myId
    ) {
        //todo: use authorization to retrieve doctor id
        return doctorApi.confirmVisit(myId, confirmVisitDto);
    }
}
