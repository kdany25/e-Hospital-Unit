package com.example.medicalunit.dtos;

import java.util.Date;

public class MedecineDto {
    private String medName;
    private Double medPrice;
    private Date medExpiration;

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public Double getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(Double medPrice) {
        this.medPrice = medPrice;
    }

    public Date getMedExpiration() {
        return medExpiration;
    }

    public void setMedExpiration(Date medExpiration) {
        this.medExpiration = medExpiration;
    }

    public MedecineDto(String medName, Double medPrice, Date medExpiration) {
        this.medName = medName;
        this.medPrice = medPrice;
        this.medExpiration = medExpiration;
    }
}
