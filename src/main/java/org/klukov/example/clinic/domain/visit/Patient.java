package org.klukov.example.clinic.domain.visit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Patient {
    @NonNull PatientId id;
    @NonNull String firstName;
    @NonNull String lastName;
    @NonNull String peselNumber;
}
