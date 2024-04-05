package org.example.dental.services;

import lombok.RequiredArgsConstructor;
import org.example.dental.dao.PaymentInfoRepository;
import org.example.dental.models.Dentist;
import org.example.dental.models.PaymentInfo;
import org.example.dental.models.enums.PaymentType;
import org.example.dental.services.files.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentInfoService implements BaseEntityServiceInt<PaymentInfo, Integer> {

    private final PaymentInfoRepository paymentInfoRepository;
    private final CsvFileService csvFileService;
    @Value("${application.csv-data-file}")
    private String filePath;

    @Override
    public PaymentInfo saveOrUpdate(PaymentInfo paymentInfo) {
        return paymentInfoRepository.save(paymentInfo);
    }

    @Override
    public List<PaymentInfo> getAll() {
        return paymentInfoRepository.findAll();
    }

    @Override
    public Optional<PaymentInfo> getById(Integer id) {
        return paymentInfoRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        paymentInfoRepository.deleteById(id);
    }

    public void uploadFromFile() {
        List<String> lines = csvFileService
                .uploadDataFromFile(filePath, PaymentInfo.getHeaders());
        if (!lines.isEmpty()) {
            for (String line : lines) {
                saveOrUpdate(parseFromString(line));
            }
        }
    }

    private PaymentInfo parseFromString(String line) {
        String[] fields = line.split(",");
        return new PaymentInfo(
                Integer.parseInt(fields[0]),
                PaymentType.valueOf(fields[1]),
                Float.parseFloat(fields[2])
                );
    }
}
