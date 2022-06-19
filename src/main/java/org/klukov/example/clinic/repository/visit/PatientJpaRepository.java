package org.klukov.example.clinic.repository.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PatientJpaRepository extends JpaRepository<PatientModel, Long> {

}
