package org.example.dental.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dental.models.Diagnosis;
import org.example.dental.services.DiagnosisService;
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
@RequestMapping("/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController implements RestControllerInt<Diagnosis, Integer> {

    private final DiagnosisService diagnosisService;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        diagnosisService.uploadDataFromFile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<Diagnosis> save(@RequestBody Diagnosis diagnosis) {
        Diagnosis savedDiagnosis = diagnosisService.saveOrUpdate(diagnosis);
        return ResponseEntity.ok(savedDiagnosis);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Diagnosis> update(@PathVariable Integer id, @RequestBody Diagnosis diagnosis) {
        Optional<Diagnosis> existingDiagnosis = diagnosisService.getById(id);
        if (existingDiagnosis.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        diagnosis.setId(id);
        Diagnosis updatedDiagnosis = diagnosisService.saveOrUpdate(diagnosis);
        return ResponseEntity.ok(updatedDiagnosis);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Diagnosis>> getAll() {
        List<Diagnosis> diagnoses = diagnosisService.getAll();
        return ResponseEntity.ok(diagnoses);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getById(@PathVariable Integer id) {
        Optional<Diagnosis> existingDiagnosis = diagnosisService.getById(id);
        return existingDiagnosis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Optional<Diagnosis> existingDiagnosis = diagnosisService.getById(id);
        if (existingDiagnosis.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        diagnosisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
