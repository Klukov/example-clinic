package org.klukov.example.clinic.repository.doctor;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.klukov.example.clinic.domain.doctor.model.Doctor;
import org.klukov.example.clinic.domain.doctor.model.DoctorSpecialization;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Table(name = "doctor")
class DoctorDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private BigDecimal rating;

    @Enumerated(EnumType.STRING)
    private DoctorSpecialization specialization;

    public Doctor toDomain() {
        return Doctor.builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .rating(getRating())
                .specialization(getSpecialization())
                .build();
    }
}
