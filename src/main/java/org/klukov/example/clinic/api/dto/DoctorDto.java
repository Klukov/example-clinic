package org.klukov.example.clinic.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.Doctor;

@Value
@Builder
@Jacksonized
public class DoctorDto {

    Long id;
    String firstName;
    String lastName;

    public static DoctorDto fromDomain(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .build();
    }
}
