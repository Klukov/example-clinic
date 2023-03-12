package org.klukov.example.clinic.domain.visit.in;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.klukov.example.clinic.domain.visit.model.VisitId;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookVisitCommand {
    @NonNull VisitId visitId;
    @NonNull Patient patient;
    String patientRemarks;

    @Value
    @Builder(toBuilder = true)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Patient {
        @NonNull String firstName;
        @NonNull String lastName;
        @NonNull String peselNumber;
        @NonNull String phone;
    }
}
