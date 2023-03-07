package org.klukov.example.clinic.domain.visit.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewPatient {
    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull String peselNumber;
}
