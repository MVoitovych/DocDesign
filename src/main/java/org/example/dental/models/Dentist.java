package org.example.dental.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.dental.models.enums.DentalSpecialties;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dentist extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private DentalSpecialties specialty;

    @ManyToOne
    private Hospital hospital;

    private static final String HEADERS = "id,specialty,hospital_id,";

    public Dentist(Integer id, String lastName, String firstName, String phoneNumber,
                   DentalSpecialties specialty, Hospital hospital) {
        super(lastName, firstName, phoneNumber);
        this.id = id;
        this.specialty = specialty;
        this.hospital = hospital;
    }

    @JsonIgnore
    public static String getHeaders() {
        return HEADERS.concat(User.getHeaders());
    }
}
