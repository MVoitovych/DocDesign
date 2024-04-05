package org.example.dental.services;

import lombok.RequiredArgsConstructor;
import org.example.dental.dao.AdditionalInformationRepository;
import org.example.dental.dao.PatientRepository;
import org.example.dental.models.AdditionalInformation;
import org.example.dental.models.Patient;
import org.example.dental.services.files.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService implements BaseEntityServiceInt<Patient, Integer> {

    private final PatientRepository patientRepository;
    private final CsvFileService csvFileService;
    private final AdditionalInformationRepository informationRepository;
    @Value("${application.csv-data-file}")
    private String filePath;

    @Override
    public Patient saveOrUpdate(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getById(Integer id) {
        return patientRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        patientRepository.deleteById(id);
    }

    public void uploadFromFile() {
        List<String> lines = csvFileService
                .uploadDataFromFile(filePath, Patient.getHeaders());
        if (!lines.isEmpty()) {
            for (String line : lines) {
                saveOrUpdate(parseFromString(line));
            }
        }

    }

    private Patient parseFromString(String line) {
        String[] fields = line.split(",");
        Optional<AdditionalInformation> optional =
                informationRepository.findById(Integer.parseInt(fields[5]));
        var additionalInformation = new AdditionalInformation();
        if (optional.isEmpty()) {
            return null;
        }
        return new Patient(
                fields[2],
                fields[1],
                fields[3],
                Integer.parseInt(fields[0]),
                Boolean.parseBoolean(fields[4]),
                additionalInformation
        );
    }
}
