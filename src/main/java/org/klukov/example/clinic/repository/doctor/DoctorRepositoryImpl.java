package org.klukov.example.clinic.repository.doctor;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.Doctor;
import org.klukov.example.clinic.domain.doctor.DoctorId;
import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;
import org.klukov.example.clinic.domain.doctor.out.DoctorRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class DoctorRepositoryImpl implements DoctorRepository {

    private final DoctorJpaRepository doctorJpaRepository;

    @Override
    public Set<Doctor> findDoctors(Collection<DoctorId> ids, Collection<DoctorSpecialization> doctorSpecializations) {
        var daoIds = ids.stream().map(DoctorId::getValue).collect(Collectors.toSet());
        return doctorJpaRepository.findAllByIdInAndSpecializationIn(daoIds, doctorSpecializations).stream()
                .map(DoctorDao::toDomain)
                .collect(Collectors.toSet());
    }
}
