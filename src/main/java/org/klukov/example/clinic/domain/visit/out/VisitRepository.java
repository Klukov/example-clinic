package org.klukov.example.clinic.domain.visit.out;

import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitId;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitQuery;

import java.util.List;
import java.util.Optional;

public interface VisitRepository {

    List<Visit> findVisits(AvailableVisitQuery availableVisitQuery);

    Optional<Visit> findVisit(VisitId visitId);

    Visit update(Visit visit);
}
