package org.klukov.example.clinic.repository.visit;

import lombok.*;
import org.klukov.example.clinic.domain.visit.Patient;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "patients")
class PatientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    public static PatientModel fromDomain(Patient patient) {
        return PatientModel.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .build();
    }

    public Patient toDomain() {
        return Patient.builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .build();
    }

}
