package org.example.dental.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CSVGenerator {

    private static final String[] DENTIST_SPECIALTIES = {"ORTHODONTIST", "PERIODONTIST", "DENTAL_SURGEON"};
    private static final String[] DIAGNOSIS_DESCRIPTIONS = {"Cavity", "Gingivitis", "Root Canal"};
    private static final String[] HOSPITAL_LOCATIONS = {"Chicago", "Miami", "Dallas"};
    private static final String[] HOSPITAL_DESCRIPTIONS = {"Dental Center", "Dental Hospital", "Oral Care Clinic"};
    private static final String[] PAYMENT_TYPES = {"CASH", "CARD"};
    private static final int NUM_ENTITIES = 150;

    private static Set<Integer> usedAdditionalInformationIds = new HashSet<>();

    private static int dentistCounter = 1;
    private static int diagnosisCounter = 1;
    private static int addInfoCounter = 1;
    private static int hospitalCounter = 1;
    private static int patientCounter = 1;
    private static int paymentInfoCounter = 1;
    private static int recorderCounter = 1;


    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java CSVGenerator <filename>");
            return;
        }
        String filename = args[0];
        generateData(filename);
        System.out.println("CSV file generated: " + filename);
    }

    public static void generateData(String filename) throws IOException {
        File file = new File(filename);
        file.createNewFile();
        try (FileWriter writer = new FileWriter(filename)) {
            writeDataWithHeaders(writer, "Diagnosis");
            writeDataWithHeaders(writer, "Dentist");
            writeDataWithHeaders(writer, "Hospital");
            writeDataWithHeaders(writer, "Patient");
            writeDataWithHeaders(writer, "PaymentInfo");
            writeDataWithHeaders(writer, "Recorder");
            writeDataWithHeaders(writer, "AdditionalInformation");
            writer.flush();
            System.out.println("Generated data successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDataWithHeaders(FileWriter writer, String className) throws IOException {
        writer.write(getHeadersForClass(className));
        for (int i = 0; i < NUM_ENTITIES; i++) {
            writer.write(generateDataForClass(className));
        }
        writer.write("\n");
    }

    private static String getHeadersForClass(String className) {
        switch (className) {
            case "Dentist":
                return "id,specialty,hospital_id,lastName,firstName,phoneNumber\n";
            case "Diagnosis":
                return "id,description\n";
            case "Hospital":
                return "id,location,description\n";
            case "Patient":
                return "id,isRegistered,additionalInformation_id,lastName,firstName,phoneNumber\n";
            case "PaymentInfo":
                return "id,paymentType,amount\n";
            case "Recorder":
                return "id,hospital_id,lastName,firstName,phoneNumber\n";
            case "AdditionalInformation":
                return "id,registrationDate,lastVisitDate,nextVisitDate,dentist,diagnosis,paymentInfo\n";
            default:
                return "";
        }
    }

    private static String generateDataForClass(String className) {
        Random random = new Random();
        StringBuilder data = new StringBuilder();
        switch (className) {
            case "Dentist":
                data.append(dentistCounter++).append(",")
                        .append("Lastname").append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append("Firstname").append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append(generatePhoneNumber()).append(",")
                        .append(DENTIST_SPECIALTIES[random.nextInt(DENTIST_SPECIALTIES.length)]).append(",")
                        .append(random.nextInt(NUM_ENTITIES) + 1).append("\n");
                break;
            case "Diagnosis":
                data.append(diagnosisCounter++).append(",")
                        .append(DIAGNOSIS_DESCRIPTIONS[random.nextInt(DIAGNOSIS_DESCRIPTIONS.length)]).append("\n");
                break;
            case "Hospital":
                data.append(hospitalCounter++).append(",")
                        .append(HOSPITAL_LOCATIONS[random.nextInt(HOSPITAL_LOCATIONS.length)]).append(",")
                        .append(HOSPITAL_DESCRIPTIONS[random.nextInt(HOSPITAL_DESCRIPTIONS.length)]).append("\n");
                break;
            case "PaymentInfo":
                data.append(paymentInfoCounter++).append(",")
                        .append(PAYMENT_TYPES[random.nextInt(PAYMENT_TYPES.length)]).append(",")
                        .append(random.nextFloat() * 500).append("\n");
                break;
            case "Recorder":
                data.append(recorderCounter++).append(",")
                        .append("Lastname").append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append("Firstname").append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append(generatePhoneNumber()).append(",")
                        .append(random.nextInt(NUM_ENTITIES) + 1).append("\n");
                break;
            case "AdditionalInformation":
                data.append(addInfoCounter++).append(",")
                        .append(generateDate()).append(",")
                        .append(generateDate()).append(",")
                        .append(generateDate()).append(",")
                        .append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append(random.nextInt(NUM_ENTITIES) + 1).append("\n");
                break;
            case "Patient":
                int additionalInfoId;
                do {
                    additionalInfoId = random.nextInt(NUM_ENTITIES) + 1;
                } while (usedAdditionalInformationIds.contains(additionalInfoId));
                usedAdditionalInformationIds.add(additionalInfoId);

                data.append(patientCounter++).append(",")
                        .append("Lastname").append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append("Firstname").append(random.nextInt(NUM_ENTITIES) + 1).append(",")
                        .append(generatePhoneNumber()).append(",")
                        .append(random.nextBoolean()).append(",")
                        .append(additionalInfoId).append("\n");
                break;
        }
        return data.toString();
    }


    private static String generatePhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }

    private static String generateDate() {
        Random random = new Random();
        int year = random.nextInt(10) + 2010;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
