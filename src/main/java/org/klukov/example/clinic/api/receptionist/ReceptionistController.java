package org.klukov.example.clinic.api.receptionist;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("receptionist/v1")
@RequiredArgsConstructor
class ReceptionistController {

    private final ReceptionistApi receptionistApi;

    @PostMapping("visit/{id}/confirm")
    public ReceptionistVisitDto confirmVisit(
            @PathVariable("id") Long visitId
    ) {
        //todo: use authorization to check if user is receptionist
        return receptionistApi.confirmVisit(visitId);
    }
}
