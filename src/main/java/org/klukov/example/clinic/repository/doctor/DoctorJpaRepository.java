package org.klukov.example.clinic.repository.doctor;

import java.util.Collection;
import java.util.List;
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DoctorJpaRepository extends JpaRepository<DoctorDao, Long> {

    List<DoctorDao> findAllByIdInAndSpecializationIn(
            Collection<Long> ids, Collection<DoctorSpecialization> doctorSpecializations);
}
