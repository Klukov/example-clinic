package org.klukov.example.clinic.api.visit;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("public/v1/visit")
@RequiredArgsConstructor
class VisitController {

    private final VisitApi visitApi;

    @GetMapping("doctors")
    List<DoctorDto> getAvailableDoctors(
            @RequestParam("from") @DateTimeFormat(iso = DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DATE_TIME) LocalDateTime to
    ) {
        return visitApi.findAvailableDoctors(from, to);
    }

    @GetMapping("available")
    List<SlotDto> getAvailableSlots(
            @RequestParam("from") LocalDateTime from,
            @RequestParam("to") LocalDateTime to,
            @RequestParam("doctor") Long doctorId
    ) {
        return visitApi.findAvailableSlots(from, to, doctorId);
    }

    @PostMapping("{id}/book")
    VisitDto bookVisit(
            @PathVariable("id") Long visitId,
            @Validated @RequestBody PatientDto patientDto
    ) {
        return visitApi.bookVisit(visitId, patientDto);
    }

}
