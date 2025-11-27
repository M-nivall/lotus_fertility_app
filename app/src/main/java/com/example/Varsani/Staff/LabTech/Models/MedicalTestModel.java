package com.example.Varsani.Staff.LabTech.Models;

public class MedicalTestModel {
    private String surrogateId;
    private String medicalDate;
    private String scheduleStatus;
    private String bloodGroup;
    private String medication;
    private String fullName;
    private String phoneNo;
    private String email;
    private String user;
    private String equipment_status;
    private String scheduleID;

    // Constructor
    public MedicalTestModel(String surrogateId, String medicalDate, String scheduleStatus, String bloodGroup,
                            String medication, String fullName, String phoneNo, String email, String user, String equipment_status, String scheduleID) {
        this.surrogateId = surrogateId;
        this.medicalDate = medicalDate;
        this.scheduleStatus = scheduleStatus;
        this.bloodGroup = bloodGroup;
        this.medication = medication;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.user = user;
        this.equipment_status = equipment_status;
        this.scheduleID = scheduleID;
    }

    public String getSurrogateId() {
        return surrogateId;
    }

    public String getMedicalDate() {
        return medicalDate;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getMedication() {
        return medication;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }
    public String getUser() {
        return user;
    }
    public String getEquipment_status() {
        return equipment_status;
    }
    public String getScheduleID() {
        return scheduleID;
    }
}
