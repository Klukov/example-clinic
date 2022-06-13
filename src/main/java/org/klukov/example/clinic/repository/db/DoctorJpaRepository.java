package org.klukov.example.clinic.repository.db;

import org.klukov.example.clinic.repository.dao.DoctorDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorDao, Long> {

}
