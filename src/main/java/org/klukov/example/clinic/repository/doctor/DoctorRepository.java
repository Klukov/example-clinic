package org.klukov.example.clinic.repository.doctor;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.Doctor;
import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorRepository {

    private final DoctorJpaRepository doctorJpaRepository;

    public Optional<Doctor> findById(Long id) {
        return doctorJpaRepository.findById(id)
                .map(DoctorDao::toDomain);
    }

    public boolean existsById(Long id) {
        return doctorJpaRepository.existsById(id);
    }

    public Set<Doctor> findAllByIdsAndSpecialization(Set<Long> doctorIds, DoctorSpecialization doctorSpecialization) {
        return doctorJpaRepository.findAllByIdInAndSpecialization(doctorIds, doctorSpecialization).stream()
                .map(DoctorDao::toDomain)
                .collect(Collectors.toSet());
    }
}