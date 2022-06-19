package org.klukov.example.clinic.repository.visit;

import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
interface VisitJpaRepository extends JpaRepository<VisitModel, Long> {

    List<VisitModel> findAllByDoctorIdAndTimeFromAfterAndTimeToBeforeAndStatusIn(
            Long doctorId,
            LocalDateTime from,
            LocalDateTime to,
            Collection<VisitStatus> statuses);

    List<VisitModel> findAllByTimeFromAfterAndTimeToBeforeAndStatusIn(
            LocalDateTime from,
            LocalDateTime to,
            Collection<VisitStatus> statuses);
}
