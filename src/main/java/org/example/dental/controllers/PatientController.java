package org.example.dental.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dental.models.Patient;
import org.example.dental.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController implements RestControllerInt<Patient, Integer> {

    private final PatientService patientService;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        patientService.uploadFromFile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<Patient> save(@RequestBody Patient patient) {
        Patient savedPatient = patientService.saveOrUpdate(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Integer id, @RequestBody Patient patient) {
        Patient existingPatient = patientService.getById(id).orElse(null);
        if (existingPatient == null) {
            return ResponseEntity.notFound().build();
        }
        patient.setId(id);
        Patient updatedPatient = patientService.saveOrUpdate(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Patient>> getAll() {
        List<Patient> patients = patientService.getAll();
        return ResponseEntity.ok(patients);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Integer id) {
        Patient patient = patientService.getById(id).orElse(null);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Patient existingPatient = patientService.getById(id).orElse(null);
        if (existingPatient == null) {
            return ResponseEntity.notFound().build();
        }
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
