package org.klukov.example.clinic.repository.doctor;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.model.Doctor;
import org.klukov.example.clinic.domain.doctor.model.DoctorId;
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization;
import org.klukov.example.clinic.domain.doctor.out.DoctorRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DoctorRepositoryImpl implements DoctorRepository {

    private final DoctorJpaRepository doctorJpaRepository;

    @Override
    public Set<Doctor> findDoctors(
            Collection<DoctorId> ids, Collection<DoctorSpecialization> doctorSpecializations) {
        var daoIds = ids.stream().map(DoctorId::getValue).collect(Collectors.toSet());
        return doctorJpaRepository
                .findAllByIdInAndSpecializationIn(daoIds, doctorSpecializations)
                .stream()
                .map(DoctorDao::toDomain)
                .collect(Collectors.toSet());
    }
}
