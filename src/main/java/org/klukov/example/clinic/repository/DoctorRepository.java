package org.klukov.example.clinic.repository;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.Doctor;
import org.klukov.example.clinic.repository.dao.DoctorDao;
import org.klukov.example.clinic.repository.db.DoctorJpaRepository;
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

    public Set<Doctor> findAllByIds(Set<Long> doctorIds) {
        return doctorJpaRepository.findAllById(doctorIds).stream()
                .map(DoctorDao::toDomain)
                .collect(Collectors.toSet());
    }
}
