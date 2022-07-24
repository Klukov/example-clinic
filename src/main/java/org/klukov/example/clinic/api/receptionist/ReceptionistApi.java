package org.klukov.example.clinic.api.receptionist;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.visit.ConfirmVisitCommand;
import org.klukov.example.clinic.domain.visit.VisitService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class ReceptionistApi {

    private final VisitService visitService;

    @Transactional
    public ReceptionistVisitDto confirmVisit(Long visitId) {
        return ReceptionistVisitDto.fromDomain(
                visitService.confirmVisit(
                        ConfirmVisitCommand.builder()
                                .visitId(visitId)
                                .build()
                ));
    }
}
