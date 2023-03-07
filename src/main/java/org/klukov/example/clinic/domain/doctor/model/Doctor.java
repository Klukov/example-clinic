package org.klukov.example.clinic.domain.doctor.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Doctor {
    Long id;
    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull DoctorSpecialization specialization;
    BigDecimal rating;
}
