package org.klukov.example.clinic.domain.visit.out;

import java.util.List;
import java.util.Optional;
import org.klukov.example.clinic.domain.visit.in.AvailableVisitQuery;
import org.klukov.example.clinic.domain.visit.model.Visit;
import org.klukov.example.clinic.domain.visit.model.VisitId;

public interface VisitRepository {

    List<Visit> findVisits(AvailableVisitQuery availableVisitQuery);

    Optional<Visit> findVisit(VisitId visitId);

    Visit update(Visit visit);
}
