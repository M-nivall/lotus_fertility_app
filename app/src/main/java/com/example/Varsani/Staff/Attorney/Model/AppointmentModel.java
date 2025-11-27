package com.example.Varsani.Staff.Attorney.Model;

public class AppointmentModel {
    private String id, scheduleId, parentId, surrogateId, partnerName, partnerContact;
    private String scheduleDate, parentName, parentPhone, parentEmail;
    private String surrogateName, surrogatePhone, surrogateEmail,schedule_type;

    // Constructor
    public AppointmentModel(String id, String scheduleId, String parentId, String surrogateId,
                            String partnerName, String partnerContact, String scheduleDate,
                            String parentName, String parentPhone, String parentEmail,
                            String surrogateName, String surrogatePhone, String surrogateEmail, String schedule_type) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.parentId = parentId;
        this.surrogateId = surrogateId;
        this.partnerName = partnerName;
        this.partnerContact = partnerContact;
        this.scheduleDate = scheduleDate;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.surrogateName = surrogateName;
        this.surrogatePhone = surrogatePhone;
        this.surrogateEmail = surrogateEmail;
        this.schedule_type = schedule_type;
    }

    // Getter Methods
    public String getId() { return id; }
    public String getScheduleId() { return scheduleId; }
    public String getParentId() { return parentId; }
    public String getSurrogateId() { return surrogateId; }
    public String getPartnerName() { return partnerName; }
    public String getPartnerContact() { return partnerContact; }
    public String getScheduleDate() { return scheduleDate; }
    public String getParentName() { return parentName; }
    public String getParentPhone() { return parentPhone; }
    public String getParentEmail() { return parentEmail; }
    public String getSurrogateName() { return surrogateName; }
    public String getSurrogatePhone() { return surrogatePhone; }
    public String getSurrogateEmail() { return surrogateEmail; }
    public String getSchedule_type() { return schedule_type; }
}

