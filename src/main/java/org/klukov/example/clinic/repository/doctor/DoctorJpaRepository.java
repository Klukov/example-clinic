package org.klukov.example.clinic.repository.doctor;

import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
interface DoctorJpaRepository extends JpaRepository<DoctorDao, Long> {

    List<DoctorDao> findAllByIdInAndSpecialization(Collection<Long> ids, DoctorSpecialization doctorSpecialization);
}
