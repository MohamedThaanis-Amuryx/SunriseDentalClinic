package com.app.model;

import java.math.BigDecimal;

public class Treatment {

    private int treatmentId;
    private String treatmentName;
    private BigDecimal consultationFee;

    public Treatment() {
    }

    public Treatment(int treatmentId, String treatmentName, BigDecimal consultationFee) {
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.consultationFee = consultationFee;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }
}