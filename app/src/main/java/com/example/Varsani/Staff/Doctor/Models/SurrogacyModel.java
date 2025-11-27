package com.example.Varsani.Staff.Doctor.Models;

public class SurrogacyModel {
    private String schedule_id, parent_id, surrogate_id, partner_name, partner_contact;
    private String schedule_date, booking_date, parent_name, parent_phone, parent_email;
    private String surrogate_name, surrogate_phone, surrogate_email,schedule_type;

    // Constructor
    public SurrogacyModel(String schedule_id, String parent_id, String surrogate_id,
                          String partner_name, String partner_contact, String schedule_date,
                          String booking_date, String parent_name, String parent_phone,
                          String parent_email, String surrogate_name, String surrogate_phone,
                          String surrogate_email, String schedule_type) {
        this.schedule_id = schedule_id;
        this.parent_id = parent_id;
        this.surrogate_id = surrogate_id;
        this.partner_name = partner_name;
        this.partner_contact = partner_contact;
        this.schedule_date = schedule_date;
        this.booking_date = booking_date;
        this.parent_name = parent_name;
        this.parent_phone = parent_phone;
        this.parent_email = parent_email;
        this.surrogate_name = surrogate_name;
        this.surrogate_phone = surrogate_phone;
        this.surrogate_email = surrogate_email;
        this.schedule_type = schedule_type;
    }

    // Getters
    public String getScheduleId() { return schedule_id; }
    public String getParentId() { return parent_id; }
    public String getSurrogateId() { return surrogate_id; }
    public String getPartnerName() { return partner_name; }
    public String getPartnerContact() { return partner_contact; }
    public String getScheduleDate() { return schedule_date; }
    public String getBookingDate() { return booking_date; }
    public String getParentName() { return parent_name; }
    public String getParentPhone() { return parent_phone; }
    public String getParentEmail() { return parent_email; }
    public String getSurrogateName() { return surrogate_name; }
    public String getSurrogatePhone() { return surrogate_phone; }
    public String getSurrogateEmail() { return surrogate_email; }
    public String getSchedule_type() { return schedule_type; }
}
