package org.klukov.example.clinic.api;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.api.dto.VisitDto;
import org.klukov.example.clinic.domain.ConfirmVisitCommand;
import org.klukov.example.clinic.domain.service.VisitService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReceptionistApi {

    private final VisitService visitService;

    @Transactional
    public VisitDto confirmVisit(Long visitId) {
        return VisitDto.fromDomain(
                visitService.confirmVisit(
                        ConfirmVisitCommand.builder()
                                .visitId(visitId)
                                .build()
                ));
    }
}
