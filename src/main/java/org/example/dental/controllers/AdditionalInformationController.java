package org.example.dental.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dental.models.AdditionalInformation;
import org.example.dental.services.AdditionalInformationService;
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
@RequestMapping("/additional-info")
@RequiredArgsConstructor
public class AdditionalInformationController implements RestControllerInt<AdditionalInformation, Integer> {

    private final AdditionalInformationService informationService;


    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        informationService.uploadFromFile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<AdditionalInformation> save(@RequestBody AdditionalInformation additionalInformation) {
        AdditionalInformation savedInformation = informationService.saveOrUpdate(additionalInformation);
        return ResponseEntity.ok(savedInformation);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AdditionalInformation> update(@PathVariable Integer id, @RequestBody AdditionalInformation additionalInformation) {
        Optional<AdditionalInformation> existingInformationOptional = informationService.getById(id);
        if (!existingInformationOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        additionalInformation.setId(id);
        AdditionalInformation updatedInformation = informationService.saveOrUpdate(additionalInformation);
        return ResponseEntity.ok(updatedInformation);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AdditionalInformation>> getAll() {
        List<AdditionalInformation> allInformation = informationService.getAll();
        return ResponseEntity.ok(allInformation);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AdditionalInformation> getById(@PathVariable Integer id) {
        Optional<AdditionalInformation> informationOptional = informationService.getById(id);
        return informationOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Optional<AdditionalInformation> informationOptional = informationService.getById(id);
        if (!informationOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        informationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
