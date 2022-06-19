package org.klukov.example.clinic.repository.doctor;

import lombok.*;
import org.klukov.example.clinic.domain.doctor.Doctor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "doctors")
class DoctorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private BigDecimal rating;

    public Doctor toDomain() {
        return Doctor.builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .rating(getRating())
                .build();
    }
}
