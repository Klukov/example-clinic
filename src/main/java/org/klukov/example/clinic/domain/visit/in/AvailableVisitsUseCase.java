package org.klukov.example.clinic.domain.visit.in;

import org.klukov.example.clinic.domain.visit.Visit;

import java.util.List;

public interface AvailableVisitsUseCase {

    List<Visit> findVisits(AvailableVisitQuery availableVisitQuery);
}
