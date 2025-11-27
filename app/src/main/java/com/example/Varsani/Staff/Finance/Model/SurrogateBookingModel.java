package com.example.Varsani.Staff.Finance.Model;
public class SurrogateBookingModel {
    private String schedule_id, parent_id, surrogate_id, partner_name, schedule_date, booking_date;
    private String service_fee, surrogate_fee, total_fee, payment_code;
    private String parent_name, parent_phone, parent_email;
    private String surrogate_name, surrogate_phone, surrogate_email;
    private String payment_status,schedule_type;

    public SurrogateBookingModel(String schedule_id, String parent_id, String surrogate_id, String partner_name,
                                 String schedule_date, String booking_date, String service_fee, String surrogate_fee,
                                 String total_fee, String payment_code, String parent_name, String parent_phone,
                                 String parent_email, String surrogate_name, String surrogate_phone,
                                 String surrogate_email, String payment_status, String schedule_type) {
        this.schedule_id = schedule_id;
        this.parent_id = parent_id;
        this.surrogate_id = surrogate_id;
        this.partner_name = partner_name;
        this.schedule_date = schedule_date;
        this.booking_date = booking_date;
        this.service_fee = service_fee;
        this.surrogate_fee = surrogate_fee;
        this.total_fee = total_fee;
        this.payment_code = payment_code;
        this.parent_name = parent_name;
        this.parent_phone = parent_phone;
        this.parent_email = parent_email;
        this.surrogate_name = surrogate_name;
        this.surrogate_phone = surrogate_phone;
        this.surrogate_email = surrogate_email;
        this.payment_status = payment_status;
        this.schedule_type = schedule_type;
    }

    public String getScheduleId() { return schedule_id; }
    public String getParentId() { return parent_id; }
    public String getSurrogateId() { return surrogate_id; }
    public String getPartnerName() { return partner_name; }
    public String getScheduleDate() { return schedule_date; }
    public String getBookingDate() { return booking_date; }
    public String getServiceFee() { return service_fee; }
    public String getSurrogateFee() { return surrogate_fee; }
    public String getTotalFee() { return total_fee; }
    public String getPaymentCode() { return payment_code; }
    public String getParentName() { return parent_name; }
    public String getParentPhone() { return parent_phone; }
    public String getParentEmail() { return parent_email; }
    public String getSurrogateName() { return surrogate_name; }
    public String getSurrogatePhone() { return surrogate_phone; }
    public String getSurrogateEmail() { return surrogate_email; }
    public String getPaymentStatus() { return payment_status; }
    public String getSchedule_type() { return schedule_type; }
}
