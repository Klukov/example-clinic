package org.klukov.example.clinic.api.patient;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
@RequestMapping("public/v1/visit")
@RequiredArgsConstructor
class PatientPublicController {

    private final PatientPublicApi patientPublicApi;

    @GetMapping("doctors")
    public List<DoctorDto> getAvailableDoctors(
            @RequestParam("from") @DateTimeFormat(iso = DATE_TIME) @NotNull LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DATE_TIME) @NotNull LocalDateTime to,
            @RequestParam("specialization") @NotNull DoctorSpecialization doctorSpecialization
    ) {
        return patientPublicApi.findAvailableDoctors(from, to, doctorSpecialization);
    }

    @GetMapping("available")
    public List<SlotDto> getAvailableSlots(
            @RequestParam("from") @DateTimeFormat(iso = DATE_TIME) @NotNull LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DATE_TIME) @NotNull LocalDateTime to,
            @RequestParam("doctor") @Min(0) long doctorId
    ) {
        return patientPublicApi.findAvailableSlots(from, to, doctorId);
    }

    @PostMapping("{id}/book")
    public PatientVisitDto bookVisit(
            @PathVariable("id") @NotNull @Min(0) Long visitId,
            @Validated @RequestBody BookVisitDto bookVisitDto
    ) {
        return patientPublicApi.bookVisit(visitId, bookVisitDto);
    }
}
