package org.example.dental.services.files;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileServiceImpl implements CsvFileService {
    @Override
    public List<String> uploadDataFromFile(String filePath, String headers) {
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                if (bufferedReader.readLine().equals(headers)) {
                    String newLine = "";
                    while ((newLine = bufferedReader.readLine()) != null && !newLine.equals("")) {
                        lines.add(newLine);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return lines;
    }
}
