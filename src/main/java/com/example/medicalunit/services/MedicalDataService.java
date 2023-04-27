package com.example.medicalunit.services;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.medicalunit.dtos.MedecineDto;
import com.example.medicalunit.dtos.PatientMedicalRecordDTO;
import com.example.medicalunit.model.Patient;
import com.example.medicalunit.model.Pharmacist;
import com.example.medicalunit.model.Physician;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * This class manages medical data and provides methods to retrieve, update and
 * add data related to medical records, patients, physicians and pharmacists
 */
public class MedicalDataService {

    // Use interfaces to define types to allow for easier refactoring
    private static final Map<String, PatientMedicalRecordDTO> patientsMedicalRecords = new HashMap<>();
    private static final Map<String, Patient> patients = new HashMap<>();
    private static final Map<String, Physician> physicians = new HashMap<>();
    private static final Map<String, Pharmacist> pharmacists = new HashMap<>();

    // assign doctor and pharmacy
    public static void assignDoctorAndPharmacy(PatientMedicalRecordDTO patientMedicalRecord) {
        patientsMedicalRecords.put(patientMedicalRecord.getPatientId(), patientMedicalRecord);
    }

    public static String createRecord(PatientMedicalRecordDTO patientMedicalRecord) {
        patientsMedicalRecords.put(patientMedicalRecord.getId(), patientMedicalRecord);
        return patientMedicalRecord.getId();
    }

    public static String createPharmacist(Pharmacist pharmacist) {
        pharmacists.put(pharmacist.getId(), pharmacist);
        return pharmacist.getId();
    }

    public static String createPhysician(Physician physician) {
        physicians.put(physician.getId(), physician);
        return physician.getId();
    }

    public static String createPatient(Patient patient) {
        patients.put(patient.getId(), patient);
        return patient.getId();
    }

    public static List<PatientMedicalRecordDTO> getPatientMedicalRecordByPhysicianId(String physicianId) {
        List<PatientMedicalRecordDTO> foundPatientsMedicalRecords = new ArrayList<>();

        for (PatientMedicalRecordDTO patientMedicalRecord : patientsMedicalRecords.values()) {

            if (patientMedicalRecord.getPhysicianId().equals(physicianId))
                foundPatientsMedicalRecords.add(patientMedicalRecord);
        }

        return foundPatientsMedicalRecords;
    }

    public static List<Physician> getAllPhysicians() {
        List<Physician> physicianList = new ArrayList<>(physicians.values());
        physicianList.sort(Comparator.comparing(Physician::getFirstName));
        return physicianList;
    }

    public static List<Pharmacist> getAllPharmacists() {
        List<Pharmacist> pharmacistList = new ArrayList<>(pharmacists.values());
        pharmacistList.sort(Comparator.comparingInt(Pharmacist::getAge));
        return pharmacistList;
    }

    public static List<PatientMedicalRecordDTO> getAllPatientsMedicalRecords() {
        return new ArrayList<>(patientsMedicalRecords.values());
    }

    public static List<PatientMedicalRecordDTO> getAllPatientsMedicalRecordsByPhysicianId(String physicianId) {
        List<PatientMedicalRecordDTO> foundPatientsMedicalRecords = new ArrayList<>();

        for (PatientMedicalRecordDTO patientMedicalRecord : patientsMedicalRecords.values()) {

            if (patientMedicalRecord.getPhysicianId() != null
                    && patientMedicalRecord.getPhysicianId().equals(physicianId))
                foundPatientsMedicalRecords.add(patientMedicalRecord);
        }

        return foundPatientsMedicalRecords;
    }

    public static List<PatientMedicalRecordDTO> getAllPatientsMedicalRecordsByPharmacistId(String pharmacistId) {
        List<PatientMedicalRecordDTO> foundPatientsMedicalRecords = new ArrayList<>();

        for (PatientMedicalRecordDTO patientMedicalRecord : patientsMedicalRecords.values()) {

            if (patientMedicalRecord.getPharmacistId() != null
                    && patientMedicalRecord.getPharmacistId().equals(pharmacistId))
                foundPatientsMedicalRecords.add(patientMedicalRecord);
        }

        return foundPatientsMedicalRecords;
    }

    public static String addMedecine(MedecineDto medDto) {
        String workingDir = System.getProperty("user.dir");
        try (CSVWriter writer = new CSVWriter(new FileWriter(workingDir + "/medecine.csv", true))) {
            String[] header = { medDto.getMedName(), medDto.getMedPrice().toString(),
                    medDto.getMedExpiration().toString() };
            writer.writeNext(header);
            return "success";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<MedecineDto> getMedecines() {
        List<MedecineDto> medecines = new ArrayList<>();
        String workingDir = System.getProperty("user.dir");
        try (CSVReader reader = new CSVReader(new FileReader(workingDir + "/medecine.csv"))) {
            String[] lineInArray;
            reader.skip(1);
            while ((lineInArray = reader.readNext()) != null) {
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = format.parse(lineInArray[2]);
                medecines.add(new MedecineDto(lineInArray[0], Double.parseDouble(lineInArray[1]), date));
            }
            return medecines;
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseEntity<byte[]> getMedsPerPatientRecords(String id) {

        try (CSVWriter writer = new CSVWriter(new FileWriter("prescription.csv"))) {
            // List<PatientMedicalRecordDTO> patientMedicalReport = MedicalDataService
            // .getAllPatientsMedicalRecordsByPatientId(id);
            PatientMedicalRecordDTO record = MedicalDataService.getPatientMedicalRecordById(id);
            record.getMedicines().forEach(y -> {
                String[] header = { y.getMedName(), y.getMedPrice().toString(), y.getMedExpiration().toString() };
                writer.writeNext(header);
            });
            Path path = Paths.get("prescription.csv");
            byte[] data = null;
            try {
                data = Files.readAllBytes(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("attachment", "file.csv");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            ResponseEntity<byte[]> response = new ResponseEntity<>(data, headers, HttpStatus.OK);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PatientMedicalRecordDTO> getAllPatientsMedicalRecordsByPatientId(String patientId) {
        List<PatientMedicalRecordDTO> foundPatientsMedicalRecords = new ArrayList<>();

        for (PatientMedicalRecordDTO patientMedicalRecord : patientsMedicalRecords.values()) {

            if (patientMedicalRecord.getPatientId() != null
                    && patientMedicalRecord.getPatientId().equals(patientId))
                foundPatientsMedicalRecords.add(patientMedicalRecord);
        }

        return foundPatientsMedicalRecords;
    }

    public static PatientMedicalRecordDTO getPatientMedicalRecordById(String medicalRecordId) {
        return patientsMedicalRecords.get(medicalRecordId);
    }

    public static PatientMedicalRecordDTO updatePatientMedicalRecordById(
            PatientMedicalRecordDTO patientMedicalRecordDTO) {
        return patientsMedicalRecords.put(patientMedicalRecordDTO.getId(), patientMedicalRecordDTO);
    }

    // get all patients
    public static Map<String, Patient> getPatients() {
        return patients;
    }

    // get patient by id
    public static Patient getPatientById(String patientId) {
        return patients.get(patientId);
    }

    // get all physicians
    public static Map<String, Physician> getPhysicians() {
        return physicians;
    }

    // get physician by id
    public static Physician getPhysicianById(String physicianId) {
        return physicians.get(physicianId);
    }

    // get pharmacist by id
    public static Pharmacist getPharmacistById(String pharmacistId) {
        return pharmacists.get(pharmacistId);
    }

    // get all pharmacists
    public static Map<String, Pharmacist> getPharmacists() {
        return pharmacists;
    }
}
