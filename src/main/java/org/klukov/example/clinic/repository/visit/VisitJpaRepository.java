package org.klukov.example.clinic.repository.visit;

import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
interface VisitJpaRepository extends JpaRepository<VisitDao, Long> {

    @Query("SELECT visit FROM VisitDao visit " +
            "WHERE visit.timeFrom >= :from " +
            "AND visit.timeFrom < :to " +
            "AND visit.status in :statuses " +
            "AND (:doctorId IS NULL OR visit.doctorId = :doctorId)" +
            "ORDER BY visit.timeFrom")
    List<VisitDao> findAllVisits(
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            @Param("statuses") Collection<VisitStatus> statuses,
            @Param("doctorId") Long doctorId
    );
}
