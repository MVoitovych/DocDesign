package org.example.dental.models;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    private String lastName;
    private String firstName;
    private String phoneNumber;

    private static final String HEADERS = "lastName,firstName,phoneNumber";

    public static String getHeaders() {
        return HEADERS;
    }
}
