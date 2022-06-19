package org.klukov.example.clinic.repository.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DoctorJpaRepository extends JpaRepository<DoctorModel, Long> {

}
