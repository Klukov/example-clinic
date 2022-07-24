package org.klukov.example.clinic.domain.doctor;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.visit.Visit;
import org.klukov.example.clinic.domain.visit.VisitStatus;
import org.klukov.example.clinic.repository.doctor.DoctorRepository;
import org.klukov.example.clinic.repository.visit.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final VisitRepository visitRepository;

    public Set<Doctor> findAllAvailableDoctors(
            LocalDateTime from,
            LocalDateTime to,
            DoctorSpecialization doctorSpecialization
    ) {
        var availableDoctorIds = visitRepository.findVisits(from, to, List.of(VisitStatus.FREE)).stream()
                .map(Visit::getDoctorId)
                .collect(Collectors.toSet());
        return doctorRepository.findAllByIdsAndSpecialization(availableDoctorIds, doctorSpecialization);
    }
}
