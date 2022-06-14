package org.klukov.example.clinic.repository.db;

import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.repository.dao.VisitDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface VisitJpaRepository extends JpaRepository<VisitDao, Long> {

    List<VisitDao> findAllByDoctorIdAndTimeFromAfterAndTimeToBeforeAndStatusIn(
            Long doctorId,
            LocalDateTime from,
            LocalDateTime to,
            Collection<VisitStatus> statuses);

    List<VisitDao> findAllByTimeFromAfterAndTimeToBeforeAndStatusIn(
            LocalDateTime from,
            LocalDateTime to,
            Collection<VisitStatus> statuses);
}
