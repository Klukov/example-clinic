package org.klukov.example.clinic.repository.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PatientJpaRepository extends JpaRepository<PatientDao, Long> {

}
