package org.klukov.example.clinic.domain.visit.in;

import java.util.List;
import org.klukov.example.clinic.domain.visit.model.Visit;

public interface AvailableVisitsUseCase {

    List<Visit> findVisits(AvailableVisitQuery availableVisitQuery);
}
