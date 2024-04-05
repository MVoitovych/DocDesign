package org.example.dental.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dental.models.Hospital;
import org.example.dental.services.HospitalService;
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
import java.util.Optional;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController implements RestControllerInt<Hospital, Integer> {

    private final HospitalService hospitalService;
    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        hospitalService.uploadDataFromFile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<Hospital> save(@RequestBody Hospital hospital) {
        Hospital savedHospital = hospitalService.saveOrUpdate(hospital);
        return ResponseEntity.ok(savedHospital);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Hospital> update(@PathVariable Integer id, @RequestBody Hospital hospital) {
        Optional<Hospital> existingHospital = hospitalService.getById(id);
        if (existingHospital.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        hospital.setId(id);
        Hospital updatedHospital = hospitalService.saveOrUpdate(hospital);
        return ResponseEntity.ok(updatedHospital);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Hospital>> getAll() {
        List<Hospital> hospitals = hospitalService.getAll();
        return ResponseEntity.ok(hospitals);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getById(@PathVariable Integer id) {
        Optional<Hospital> hospital = hospitalService.getById(id);
        return hospital.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Optional<Hospital> existingHospital = hospitalService.getById(id);
        if (existingHospital.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        hospitalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
