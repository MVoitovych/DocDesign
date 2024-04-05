package org.example.dental.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dental.models.Dentist;
import org.example.dental.services.DentistService;
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
@RequestMapping("/dentists")
@RequiredArgsConstructor
public class DentistController implements RestControllerInt<Dentist, Integer> {

    private final DentistService dentistService;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        dentistService.uploadDataFromFile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<Dentist> save(@RequestBody Dentist dentist) {
        Dentist savedDentist = dentistService.saveOrUpdate(dentist);
        return ResponseEntity.ok(savedDentist);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Dentist> update(@PathVariable Integer id, @RequestBody Dentist dentist) {
        Optional<Dentist> existingDentistOptional = dentistService.getById(id);
        if (!existingDentistOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dentist.setId(id);
        Dentist updatedDentist = dentistService.saveOrUpdate(dentist);
        return ResponseEntity.ok(updatedDentist);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Dentist>> getAll() {
        List<Dentist> dentists = dentistService.getAll();
        return ResponseEntity.ok(dentists);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Dentist> getById(@PathVariable Integer id) {
        Optional<Dentist> dentist = dentistService.getById(id);
        return dentist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        dentistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

