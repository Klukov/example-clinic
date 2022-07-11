package org.klukov.example.clinic.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Doctor {
    Long id;
    String firstName;
    String lastName;
    BigDecimal rating;
    DoctorSpecialization specialization;
}
