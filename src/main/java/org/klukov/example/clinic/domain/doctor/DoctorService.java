package org.klukov.example.clinic.domain.doctor;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.klukov.example.clinic.domain.doctor.in.AvailableDoctorsQuery;
import org.klukov.example.clinic.domain.doctor.in.AvailableDoctorsUseCase;
import org.klukov.example.clinic.domain.doctor.model.Doctor;
import org.klukov.example.clinic.domain.doctor.out.DoctorAvailabilityRepository;
import org.klukov.example.clinic.domain.doctor.out.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DoctorService implements AvailableDoctorsUseCase {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorRepository doctorRepository;

    public Set<Doctor> findAll(AvailableDoctorsQuery availableDoctorsQuery) {
        var availableDoctors =
                doctorAvailabilityRepository.findAllAvailableDoctors(
                        availableDoctorsQuery.getFrom(), availableDoctorsQuery.getTo());
        return doctorRepository.findDoctors(
                availableDoctors, availableDoctorsQuery.getDoctorSpecializations());
    }
}
