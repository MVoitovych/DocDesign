package org.example.dental.services;

import lombok.RequiredArgsConstructor;
import org.example.dental.dao.HospitalRepository;
import org.example.dental.models.Hospital;
import org.example.dental.services.files.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalService implements BaseEntityServiceInt<Hospital, Integer> {

    private final HospitalRepository hospitalRepository;
    private final CsvFileService csvFileService;
    @Value("${application.csv-data-file}")
    private String filePath;

    @Override
    public Hospital saveOrUpdate(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public List<Hospital> getAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public Optional<Hospital> getById(Integer id) {
        return hospitalRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        hospitalRepository.deleteById(id);
    }

    public void uploadDataFromFile() {
        List<String> lines = csvFileService
                .uploadDataFromFile(filePath, Hospital.getHeaders());
        if (!lines.isEmpty()) {
            for (String line : lines) {
                saveOrUpdate(parseFromString(line));
            }
        }
    }

    private Hospital parseFromString(String dentistString) {
        String[] fields = dentistString.split(",");
        return new Hospital(
                Integer.parseInt(fields[0]),
                fields[1],
                fields[2]
        );
    }
}
