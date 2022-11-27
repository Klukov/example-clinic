package org.klukov.example.clinic.api.patient;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.doctor.Doctor;
import org.klukov.example.clinic.domain.doctor.DoctorSpecialization;

@Value
@Builder
@Jacksonized
class DoctorDto {

    @NonNull Long id;
    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull DoctorSpecialization doctorSpecialization;

    public static DoctorDto fromDomain(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .doctorSpecialization(doctor.getSpecialization())
                .build();
    }
}
