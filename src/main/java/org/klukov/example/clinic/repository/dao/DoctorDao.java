package org.klukov.example.clinic.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.klukov.example.clinic.domain.Doctor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorDao {

    @Id
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany()
    private List<VisitDao> visits = new ArrayList<>();

    public Doctor toDomain() {
        return Doctor.builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .build();
    }
}
