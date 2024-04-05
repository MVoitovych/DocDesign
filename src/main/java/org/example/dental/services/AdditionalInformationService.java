package org.example.dental.services;

import lombok.RequiredArgsConstructor;
import org.example.dental.dao.AdditionalInformationRepository;
import org.example.dental.dao.DentistRepository;
import org.example.dental.dao.DiagnosisRepository;
import org.example.dental.dao.PaymentInfoRepository;
import org.example.dental.models.AdditionalInformation;
import org.example.dental.models.Dentist;
import org.example.dental.models.Diagnosis;
import org.example.dental.models.PaymentInfo;
import org.example.dental.services.files.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdditionalInformationService implements BaseEntityServiceInt<AdditionalInformation, Integer> {

    private final AdditionalInformationRepository additionalInformationRepository;
    @Value("${application.csv-data-file}")
    private String filePath;
    private final CsvFileService csvFileService;
    private final DentistRepository dentistRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    @Override
    public AdditionalInformation saveOrUpdate(AdditionalInformation additionalInformation) {
        return additionalInformationRepository.save(additionalInformation);
    }

    @Override
    public List<AdditionalInformation> getAll() {
        return additionalInformationRepository.findAll();
    }

    @Override
    public Optional<AdditionalInformation> getById(Integer id) {
        return additionalInformationRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        additionalInformationRepository.deleteById(id);
    }

    public void uploadFromFile() {
        List<String> lines = csvFileService
                .uploadDataFromFile(filePath, AdditionalInformation.getHeaders());
        if (!lines.isEmpty()) {
            for (String line : lines) {
                saveOrUpdate(parseFromString(line));
            }
        }
    }

    private AdditionalInformation parseFromString(String line) {
        String[] fields = line.split(",");
        Dentist dentist = dentistRepository.findById(Integer.parseInt(fields[4])).get();
        Diagnosis diagnosis = diagnosisRepository.findById(Integer.parseInt(fields[5])).get();
        PaymentInfo paymentInfo = paymentInfoRepository.findById(Integer.parseInt(fields[6])).get();
        return new AdditionalInformation(
                Integer.parseInt(fields[0]),
                LocalDate.parse(fields[1]),
                LocalDate.parse(fields[2]),
                LocalDate.parse(fields[3]),
                dentist,
                diagnosis,
                paymentInfo
        );
    }
}
