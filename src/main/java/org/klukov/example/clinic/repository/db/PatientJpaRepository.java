package org.klukov.example.clinic.repository.db;

import org.klukov.example.clinic.repository.dao.PatientDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientDao, Long> {

}
