package org.example.dental.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate registrationDate;
    private LocalDate lastVisitDate;
    private LocalDate nextVisitDate;
    @ManyToOne
    private Dentist dentist;
    @ManyToOne
    private Diagnosis diagnosis;
    @ManyToOne
    private PaymentInfo paymentInfo;

    private static final String HEADERS = "id,registrationDate,lastVisitDate," +
            "nextVisitDate,dentist,diagnosis,paymentInfo";

    @JsonIgnore
    public static String getHeaders() {
        return HEADERS;
    }
}
