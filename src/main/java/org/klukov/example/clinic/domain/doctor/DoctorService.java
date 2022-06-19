package org.klukov.example.clinic.domain.doctor;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.repository.doctor.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public boolean doctorExists(Long doctorId) {
        return doctorRepository.existsById(doctorId);
    }

    public Set<Doctor> findAllByIds(Set<Long> doctorIds) {
        return doctorRepository.findAllByIds(doctorIds);
    }
}
