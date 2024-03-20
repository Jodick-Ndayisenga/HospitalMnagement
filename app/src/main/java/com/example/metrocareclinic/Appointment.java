package com.example.metrocareclinic;

import androidx.annotation.NonNull;

public class Appointment {
    String id, doc, docFullName, field, time, patients, medicines;

    public  Appointment(){};

    public String getMedicines() {
        return medicines;
    }

    public String getId() {
        return id;
    }


    public String theDocName() {
        return doc;
    }

    public String theDocField() {
        return field;
    }

    public String getDocFullName() {
        return docFullName;
    }

    public String getField() {
        return field;
    }

    public String getDoc() {
        return doc;
    }

    public String getTime() {
        return time;
    }

    public String getPatients() {
        return patients;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPatients(String patients) {
        this.patients = patients;
    }

    public void setDocFullName(String docFullName) {
        this.docFullName = docFullName;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", doc='" + doc + '\'' +
                ", docFullName='" + docFullName + '\'' +
                ", field='" + field + '\'' +
                ", time='" + time + '\'' +
                ", patients='" + patients + '\'' +
                ", medicines='" + medicines + '\'' +
                '}';
    }
}
