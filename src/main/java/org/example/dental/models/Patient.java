package org.example.dental.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean isRegistered;
    @OneToOne(cascade = CascadeType.ALL)
    private AdditionalInformation additionalInformation;

    private static final String HEADERS = "id,isRegistered,additionalInformation_id,";

    public Patient(String lastName, String firstName, String phoneNumber,
                   Integer id, boolean isRegistered, AdditionalInformation additionalInformation) {
        super(lastName, firstName, phoneNumber);
        this.id = id;
        this.isRegistered = isRegistered;
        this.additionalInformation = additionalInformation;
    }

    @JsonIgnore
    public static String getHeaders() {
        return HEADERS.concat(User.getHeaders());
    }
}