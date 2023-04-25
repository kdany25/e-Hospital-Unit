package com.example.medicalunit.dtos;

import java.util.UUID;

public class PatientMedicalRecordDTO {

    private final String id = UUID.randomUUID().toString();
    private String patientId;
    private String symptoms;
    private String physicianId;
    private String pharmacistId;
    private String consultation;
    private String medicines;

    public PatientMedicalRecordDTO(
           String patientId,
            String symptoms) {

        this.patientId = patientId;
        this.symptoms = symptoms;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getConsultation() {
        return consultation;
    }

    public void setConsultation(String consultation) {
        this.consultation = consultation;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(String physicianId) {
        this.physicianId = physicianId;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    @Override
    public String toString() {
        return "PatientMedicalRecordDTO [id=" + id + ", patientId=" + patientId + ", symptoms=" + symptoms
                + ", physicianId=" + physicianId + ", pharmacistId=" + pharmacistId + ", consultation=" + consultation
                + ", medicines=" + medicines + "]";
    }
}
