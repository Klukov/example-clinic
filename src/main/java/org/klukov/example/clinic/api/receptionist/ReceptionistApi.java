package org.klukov.example.clinic.api.receptionist;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.visit.model.VisitId;
import org.klukov.example.clinic.domain.visit.in.ConfirmVisitCommand;
import org.klukov.example.clinic.domain.visit.in.ConfirmVisitUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class ReceptionistApi {

    private final ConfirmVisitUseCase confirmVisitUseCase;

    @Transactional
    public ReceptionistVisitDto confirmVisit(long visitId) {
        return ReceptionistVisitDto.fromDomain(
                confirmVisitUseCase.confirmVisit(
                        ConfirmVisitCommand.builder()
                                .visitId(VisitId.of(visitId))
                                .build()
                ));
    }
}
