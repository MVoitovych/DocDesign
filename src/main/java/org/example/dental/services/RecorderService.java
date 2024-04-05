package org.example.dental.services;

import lombok.RequiredArgsConstructor;
import org.example.dental.dao.HospitalRepository;
import org.example.dental.dao.RecorderRepository;
import org.example.dental.models.Hospital;
import org.example.dental.models.Recorder;
import org.example.dental.services.files.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecorderService implements BaseEntityServiceInt<Recorder, Integer> {

    private final RecorderRepository recorderRepository;
    private final CsvFileService csvFileService;
    private final HospitalRepository hospitalRepository;
    @Value("${application.csv-data-file}")
    private String filePath;

    public void uploadFromFile() {
        List<String> lines = csvFileService
                .uploadDataFromFile(filePath, Recorder.getHeaders());
        if (!lines.isEmpty()) {
            for (String line : lines) {
                saveOrUpdate(parseFromString(line));
            }
        }
    }

    private Recorder parseFromString(String line) {
        String[] fields = line.split(",");
        Hospital hospital = hospitalRepository.getReferenceById(Integer.parseInt(fields[4]));
        return new Recorder(
                fields[1],
                fields[2],
                fields[3],
                Integer.parseInt(fields[0]),
                hospital
        );
    }

    @Override
    public Recorder saveOrUpdate(Recorder recorder) {
        return recorderRepository.save(recorder);
    }

    @Override
    public List<Recorder> getAll() {
        return recorderRepository.findAll();
    }

    @Override
    public Optional<Recorder> getById(Integer id) {
        return recorderRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        recorderRepository.deleteById(id);
    }
}

