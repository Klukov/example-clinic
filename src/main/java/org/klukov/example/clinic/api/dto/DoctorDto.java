package org.klukov.example.clinic.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.Doctor;
import org.klukov.example.clinic.domain.DoctorSpecialization;

@Value
@Builder
@Jacksonized
public class DoctorDto {

    Long id;
    String firstName;
    String lastName;
    DoctorSpecialization doctorSpecialization;

    public static DoctorDto fromDomain(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .doctorSpecialization(doctor.getSpecialization())
                .build();
    }
}
