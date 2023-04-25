package com.example.medicalunit.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.medicalunit.dtos.PatientMedicalRecordDTO;
import com.example.medicalunit.model.Patient;
import com.example.medicalunit.model.Pharmacist;
import com.example.medicalunit.model.Physician;
import com.example.medicalunit.model.Gender;

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

    /**
     * This method populates the patients map with sample data
     */
    public static void populatePatients() {
        Patient p1 = new Patient("8f99a086-05df-48da-9a00-5a3d7729aff1", "muhire", "philippe", "philly",
                "philipe@gmail.com", "PATIENT", 18, Gender.MALE);

        Patient p2 = new Patient("9210f0a5-c8b2-44e9-9702-62d40a0baa93", "habimana", "yannick", "yanny",
                "yannick@gmail.com", "PATIENT", 29, Gender.MALE);

        patients.put(p1.getId(), p1);
        patients.put(p2.getId(), p2);
    }

    /**
     * This method populates the physicians map with sample data
     */
    public static void populatePhysicians() {
        Physician p1 = new Physician("2d8e1969-8a78-4bb8-b5ff-7b0d7222c7fd", "umurerwa", "gisele", "gigi",
        "gisele@gmail.com", "PHYSICIAN", 41, Gender.FEMALE);
        Physician p2 = new Physician("d2aac6aa-019a-4933-b152-5090b7ac56d4", "uwamahoro", "henriette", "henry",
        "henrye@gmail.com", "PHYSICIAN", 49, Gender.FEMALE);

        physicians.put(p1.getId(), p1);
        physicians.put(p2.getId(), p2);
    }

    /**
     * This method populates the pharmacists map with sample data
     */
    public static void populatePharmacists() {
        Pharmacist p1 = new Pharmacist("485b00e6-6d81-4dde-ae40-a1327f521428", "dusinge", "felix", "fely",
        "felixi@gmail.com", "PHARMACIST", 26, Gender.MALE);
        Pharmacist p2 = new Pharmacist("20c14acc-3581-4366-9f4d-b7ee370016ec", "kwizera", "maniple", "manip",
        "maniple@gmail.com", "PHARMACIST", 29, Gender.FEMALE);

        pharmacists.put(p1.getId(), p1);
        pharmacists.put(p2.getId(), p2);
    }

    /**
     * This method populates the patientsMedicalRecords map with sample data
     */
    public static void populatePatientMedicalRecords() {
        PatientMedicalRecordDTO p1 = new PatientMedicalRecordDTO("57282g2bbh", "8f99a086-05df-48da-9a00-5a3d7729aff1", "Cough and fever");
        PatientMedicalRecordDTO p2 = new PatientMedicalRecordDTO("672g2by272", "9210f0a5-c8b2-44e9-9702-62d40a0baa93", "Typhoid");

        patientsMedicalRecords.put(p1.getId(), p1);
        patientsMedicalRecords.put(p2.getId(), p2);
    }

    // assign doctor and pharmacy
    public static void assignDoctorAndPharmacy(PatientMedicalRecordDTO patientMedicalRecord) {
        patientsMedicalRecords.put(patientMedicalRecord.getPatientId(), patientMedicalRecord);
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
        return new ArrayList<>(physicians.values());
    }

    public static List<Pharmacist> getAllPharmacists() {
        return new ArrayList<>(pharmacists.values());
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
