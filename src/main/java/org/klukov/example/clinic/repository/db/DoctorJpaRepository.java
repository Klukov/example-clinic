package org.klukov.example.clinic.repository.db;

import org.klukov.example.clinic.domain.DoctorSpecialization;
import org.klukov.example.clinic.repository.dao.DoctorDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorDao, Long> {

    List<DoctorDao> findAllByIdInAndSpecialization(Collection<Long> ids, DoctorSpecialization doctorSpecialization);
}
