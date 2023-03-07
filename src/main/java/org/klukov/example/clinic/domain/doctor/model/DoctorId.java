package org.klukov.example.clinic.domain.doctor.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class DoctorId {
    Long value;
}
