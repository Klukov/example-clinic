package org.klukov.example.clinic.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Patient {
    Long id;
    String firstName;
    String lastName;
    String peselNumber;
}
