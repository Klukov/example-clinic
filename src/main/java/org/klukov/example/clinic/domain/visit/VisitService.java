package org.klukov.example.clinic.domain.visit;

import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.Doctor;
import org.klukov.example.clinic.domain.doctor.DoctorService;
import org.klukov.example.clinic.repository.visit.PatientRepository;
import org.klukov.example.clinic.repository.visit.VisitRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;

    public List<Visit> findVisits(
            LocalDateTime from,
            LocalDateTime to,
            Long doctorId,
            Collection<VisitStatus> statuses
    ) {
        return visitRepository.findVisits(from, to, doctorId, statuses);
    }

    public Set<Doctor> findAllAvailableDoctors(
            LocalDateTime from,
            LocalDateTime to
    ) {
        var availableDoctorIds = visitRepository.findVisits(from, to, VisitStatus.all()).stream()
                .map(Visit::getDoctorId)
                .collect(Collectors.toSet());
        return doctorService.findAllByIds(availableDoctorIds);
    }


    public Visit confirmVisit(Long doctorId, Long visitId) {
        if (!doctorService.doctorExists(doctorId)) {
            throw new RuntimeException("Doctor does not exist");
        }
        var visit = visitRepository.findVisit(visitId)
                .filter(v -> VisitStatus.OCCUPIED.equals(v.getVisitStatus()))
                .filter(v -> Objects.equals(v.getDoctorId(), doctorId))
                .map(Visit::toBuilder)
                .orElseThrow(() -> new RuntimeException("visit do not exists or is in the wrong state"))
                .visitStatus(VisitStatus.CONFIRMED);
        return visitRepository.saveVisit(visit.build());
    }

    public Visit bookVisit(Long visitId, Patient patient) {
        var visitBuilder = visitRepository.findVisit(visitId)
                .filter(visit -> visit.getVisitStatus() == VisitStatus.FREE)
                .map(Visit::toBuilder)
                .orElseThrow(() -> new RuntimeException("Visit do not exists or is in the wrong status"));
        var createdPatient = patientRepository.save(patient);
        var newVisit = visitBuilder
                .visitStatus(VisitStatus.OCCUPIED)
                .patientId(createdPatient.getId())
                .build();
        return visitRepository.saveVisit(newVisit);
    }
}
