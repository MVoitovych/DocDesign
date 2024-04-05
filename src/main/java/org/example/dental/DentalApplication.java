package org.example.dental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example.dental"})
public class DentalApplication {
    public static void main(String[] args) {
        SpringApplication.run(DentalApplication.class, args);
    }
}
