package com.example.Varsani.Staff.Pharmacist.Model;

public class PrescriptionModel {
    private String scheduleId, parentId, surrogateId, partnerName, partnerContact, scheduleDate,prescription,schedule_type,medicine_status;

    // Constructor
    public PrescriptionModel(String scheduleId, String parentId, String surrogateId,
                            String partnerName, String partnerContact, String scheduleDate,
                             String prescription, String schedule_type,String medicine_status) {
        this.scheduleId = scheduleId;
        this.parentId = parentId;
        this.surrogateId = surrogateId;
        this.partnerName = partnerName;
        this.partnerContact = partnerContact;
        this.scheduleDate = scheduleDate;
        this.prescription = prescription;
        this.schedule_type = schedule_type;
        this.medicine_status = medicine_status;

    }

    // Getter Methods
    public String getScheduleId() { return scheduleId; }
    public String getParentId() { return parentId; }
    public String getSurrogateId() { return surrogateId; }
    public String getPartnerName() { return partnerName; }
    public String getPartnerContact() { return partnerContact; }
    public String getScheduleDate() { return scheduleDate; }
    public String getPrescription() { return prescription; }
    public String getSchedule_type() { return schedule_type; }
    public String getMedicine_status() { return medicine_status; }

}
