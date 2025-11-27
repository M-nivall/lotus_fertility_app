package com.example.Varsani.IntendedParent.Models;

public class MyBookingModal {
    private String schedule_id, surrogate_id, partner_name, partner_contact;
    private String schedule_date, booking_date, service_fee, surrogate_fee;
    private String total_fee, payment_code, schedule_status,full_name,schedule_type;

    public MyBookingModal(String schedule_id, String surrogate_id, String partner_name, String partner_contact,
                          String schedule_date, String booking_date, String service_fee, String surrogate_fee,
                          String total_fee, String payment_code, String schedule_status, String full_name, String schedule_type) {
        this.schedule_id = schedule_id;
        this.surrogate_id = surrogate_id;
        this.partner_name = partner_name;
        this.partner_contact = partner_contact;
        this.schedule_date = schedule_date;
        this.booking_date = booking_date;
        this.service_fee = service_fee;
        this.surrogate_fee = surrogate_fee;
        this.total_fee = total_fee;
        this.payment_code = payment_code;
        this.schedule_status = schedule_status;
        this.full_name = full_name;
        this.schedule_type = schedule_type;
    }

    // Getter methods
    public String getScheduleId() { return schedule_id; }
    public String getSurrogateId() { return surrogate_id; }
    public String getPartnerName() { return partner_name; }
    public String getPartnerContact() { return partner_contact; }
    public String getScheduleDate() { return schedule_date; }
    public String getBookingDate() { return booking_date; }
    public String getServiceFee() { return service_fee; }
    public String getSurrogateFee() { return surrogate_fee; }
    public String getTotalFee() { return total_fee; }
    public String getPaymentCode() { return payment_code; }
    public String getScheduleStatus() { return schedule_status; }
    public String getFull_name() { return full_name; }
    public String getSchedule_type() { return schedule_type; }
}

