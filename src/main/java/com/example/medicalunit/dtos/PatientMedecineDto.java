package com.example.medicalunit.dtos;

import java.util.Date;

public class PatientMedecineDto extends MedecineDto{
private String recordId;
private String pharmacistId;

    public PatientMedecineDto(String medName, Double medPrice, Date medExpiration) {
        super(medName, medPrice, medExpiration);
    }
    public PatientMedecineDto(String recordId, String medName, Double medPrice, Date medExpiration) {
        super(medName, medPrice, medExpiration);
        this.recordId = recordId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId) {
        this.pharmacistId = pharmacistId;
    }
}
