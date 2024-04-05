package org.example.dental.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recorder extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Hospital hospital;
    private static final String HEADERS = "id,hospital_id,";

    public Recorder(String lastName, String firstName, String phoneNumber,
                    Integer id, Hospital hospital) {
        super(lastName, firstName, phoneNumber);
        this.id = id;
        this.hospital = hospital;
    }

    @JsonIgnore
    public static String getHeaders() {
        return HEADERS.concat(User.getHeaders());
    }
}
