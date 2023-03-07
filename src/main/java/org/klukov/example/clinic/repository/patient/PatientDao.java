package org.klukov.example.clinic.repository.patient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.klukov.example.clinic.domain.visit.model.NewPatient;
import org.klukov.example.clinic.domain.visit.model.Patient;
import org.klukov.example.clinic.domain.visit.model.PatientId;

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

    @NotNull private String firstName;
    @NotNull private String lastName;
    @NotNull private String peselNumber;

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
