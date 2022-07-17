package org.klukov.example.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.ReceptionistApi;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.klukov.example.clinic.domain.exception.VisitRuntimeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("receptionist/v1")
@RequiredArgsConstructor
public class ReceptionistController {

    private final ReceptionistApi receptionistApi;

    @PostMapping("visit/{id}/confirm")
    @ExceptionHandler({VisitRuntimeException.class})
    public VisitDto confirmVisit(
            @PathVariable("id") Long visitId
    ) {
        //todo: use authorization to check if user is receptionist
        return receptionistApi.confirmVisit(visitId);
    }
}
