package com.example.metrocareclinic;

public class PrescriptionMedecine {
    String fullName, field, recommendation;

    public  PrescriptionMedecine(){};

    public String getFullName() {
        return fullName;
    }

    public String getField() {
        return field;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
