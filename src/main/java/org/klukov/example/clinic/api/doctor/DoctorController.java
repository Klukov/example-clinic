package org.klukov.example.clinic.api.doctor;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctor/v1")
@RequiredArgsConstructor
class DoctorController {

    private final DoctorApi doctorApi;

    @GetMapping("my-visits")
    public List<DoctorVisitDto> getMyVisits(
            @RequestParam("from") @DateTimeFormat(iso = DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DATE) LocalDate to,
            @RequestParam("my-id") @Min(0) long myId // todo: use authorization to retrieve doctor id
            ) {
        return doctorApi.findAllMyVisits(from, to, myId);
    }
}
