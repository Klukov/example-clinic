package org.klukov.example.clinic.domain.visit.in;

import org.klukov.example.clinic.domain.visit.model.Visit;

public interface BookVisitUseCase {

    Visit bookVisit(BookVisitCommand bookVisitCommand);
}
