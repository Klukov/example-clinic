package org.klukov.example.clinic.repository.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.klukov.example.clinic.domain.visit.NewPatient;
import org.klukov.example.clinic.domain.visit.Patient;
import org.klukov.example.clinic.domain.visit.PatientId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "patient")
class PatientDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String peselNumber;

    public static PatientDao fromDomain(NewPatient patient) {
        return PatientDao.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .peselNumber(patient.getPeselNumber())
                .build();
    }

    public Patient toDomain() {
        return Patient.builder()
                .id(PatientId.of(id))
                .firstName(getFirstName())
                .lastName(getLastName())
                .peselNumber(getPeselNumber())
                .build();
    }

}
