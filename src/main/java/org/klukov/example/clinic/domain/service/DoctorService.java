package org.klukov.example.clinic.domain.service;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.Doctor;
import org.klukov.example.clinic.domain.Visit;
import org.klukov.example.clinic.domain.VisitStatus;
import org.klukov.example.clinic.repository.DoctorRepository;
import org.klukov.example.clinic.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final VisitRepository visitRepository;

    public Visit confirmVisit(Long doctorId, Long visitId) {
        if (!doctorRepository.existsById(doctorId)) throw new RuntimeException("Doctor does not exist");
        var visit = visitRepository.findVisit(visitId)
                .filter(v -> VisitStatus.OCCUPIED.equals(v.getVisitStatus()))
                .filter(v -> Objects.equals(v.getDoctorId(), doctorId))
                .map(Visit::toBuilder)
                .orElseThrow(() -> new RuntimeException("visit do not exists or is in the wrong state"))
                .visitStatus(VisitStatus.CONFIRMED);
        return visitRepository.saveVisit(visit.build());
    }

    public Set<Doctor> findAllAvailableDoctors(
            LocalDateTime from,
            LocalDateTime to
    ) {
        var availableDoctorIds = visitRepository.findVisits(from, to, List.of(VisitStatus.FREE)).stream()
                .map(Visit::getDoctorId)
                .collect(Collectors.toSet());
        return doctorRepository.findAllByIds(availableDoctorIds);
    }
}
