package org.example.dental.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dental.models.Recorder;
import org.example.dental.services.RecorderService;
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
@RequiredArgsConstructor
@RequestMapping("/recorders")
public class RecorderController implements RestControllerInt<Recorder, Integer> {

    private final RecorderService recorderService;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        recorderService.uploadFromFile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<Recorder> save(@RequestBody Recorder recorder) {
        Recorder savedRecorder = recorderService.saveOrUpdate(recorder);
        return ResponseEntity.ok(savedRecorder);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Recorder> update(@PathVariable Integer id, @RequestBody Recorder recorder) {
        Recorder existingRecorder = recorderService.getById(id).orElse(null);
        if (existingRecorder == null) {
            return ResponseEntity.notFound().build();
        }
        recorder.setId(id);
        Recorder updatedRecorder = recorderService.saveOrUpdate(recorder);
        return ResponseEntity.ok(updatedRecorder);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Recorder>> getAll() {
        List<Recorder> recorders = recorderService.getAll();
        return ResponseEntity.ok(recorders);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Recorder> getById(@PathVariable Integer id) {
        Recorder recorder = recorderService.getById(id).orElse(null);
        if (recorder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recorder);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Recorder existingRecorder = recorderService.getById(id).orElse(null);
        if (existingRecorder == null) {
            return ResponseEntity.notFound().build();
        }
        recorderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
