package org.klukov.example.clinic.api.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("doctor/v1")
@RequiredArgsConstructor
class DoctorController {

    private final DoctorApi doctorApi;

    @GetMapping("my-visits")
    List<VisitDto> getMyVisits(
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to,
            @RequestParam("my-id") Long myId
    ) {
        //todo: use authorization to retrieve doctor id
        return doctorApi.findAllMyVisits(from, to, myId);
    }

    @PostMapping("visit/confirm")
    VisitDto confirmVisit(
            @Validated @RequestBody ConfirmVisitDto confirmVisitDto,
            @RequestParam Long myId
    ) {
        //todo: use authorization to retrieve doctor id
        return doctorApi.confirmVisit(myId, confirmVisitDto);
    }
}
