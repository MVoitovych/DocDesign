package org.example.dental.services;

import lombok.RequiredArgsConstructor;
import org.example.dental.dao.DentistRepository;
import org.example.dental.dao.HospitalRepository;
import org.example.dental.models.Dentist;
import org.example.dental.models.Hospital;
import org.example.dental.models.enums.DentalSpecialties;
import org.example.dental.services.files.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DentistService implements BaseEntityServiceInt<Dentist, Integer> {

    private final DentistRepository dentistRepository;
    private final HospitalRepository hospitalRepository;
    private final CsvFileService csvFileService;
    @Value("${application.csv-data-file}")
    private String filePath;

    @Override
    public Dentist saveOrUpdate(Dentist dentist) {
        return dentistRepository.save(dentist);
    }

    @Override
    public List<Dentist> getAll() {
        return dentistRepository.findAll();
    }

    @Override
    public Optional<Dentist> getById(Integer id) {
        return dentistRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        dentistRepository.deleteById(id);
    }

    public void uploadDataFromFile() {
        List<String> lines = csvFileService
                .uploadDataFromFile(filePath, Dentist.getHeaders());
        if (!lines.isEmpty()) {
            for (String line : lines) {
                saveOrUpdate(parseFromString(line));
            }
        }
    }

    private Dentist parseFromString(String dentistString) {
        String[] fields = dentistString.split(",");
        Hospital hospital = hospitalRepository.getReferenceById(Integer.parseInt(fields[5]));
        return new Dentist(
                Integer.parseInt(fields[0]),
                fields[2],
                fields[1],
                fields[3],
                DentalSpecialties.valueOf(fields[4]),
                hospital
        );
    }
}
