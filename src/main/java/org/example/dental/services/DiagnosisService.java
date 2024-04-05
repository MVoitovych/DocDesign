package org.example.dental.services;

import lombok.RequiredArgsConstructor;
import org.example.dental.dao.DiagnosisRepository;
import org.example.dental.models.Diagnosis;
import org.example.dental.services.files.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnosisService implements BaseEntityServiceInt<Diagnosis, Integer> {

    private final DiagnosisRepository diagnosisRepository;
    private final CsvFileService csvFileService;

    @Value("${application.csv-data-file}")
    private String filePath;

    @Override
    public Diagnosis saveOrUpdate(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public List<Diagnosis> getAll() {
        return diagnosisRepository.findAll();
    }

    @Override
    public Optional<Diagnosis> getById(Integer id) {
        return diagnosisRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        diagnosisRepository.deleteById(id);
    }

    public void uploadDataFromFile() {
        List<String> lines = csvFileService
                .uploadDataFromFile(filePath, Diagnosis.getHeaders());
        if (!lines.isEmpty()) {
            for (String line : lines) {
                saveOrUpdate(parseFromString(line));
            }
        }
    }

    private Diagnosis parseFromString(String dignosisString) {
        String[] fields = dignosisString.split(",");
        return new Diagnosis(
                Integer.parseInt(fields[0]),
                String.valueOf(fields[1])
        );
    }

}
