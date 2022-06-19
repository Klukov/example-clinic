package org.klukov.example.clinic.api.visit;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.klukov.example.clinic.domain.doctor.Doctor;

@Value
@Builder
@Jacksonized
class DoctorDto {

    Long id;
    String firstName;
    String lastName;

    static DoctorDto fromDomain(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .build();
    }
}
