package org.example.dental.services.files;

import java.util.List;

public interface CsvFileService {

    List<String> uploadDataFromFile(String filePath, String headers);
}
