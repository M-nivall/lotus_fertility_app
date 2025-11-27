package com.example.Varsani.IntendedParent.Models;

public class InvoiceModal {
    private String schedule_id;
    private String schedule_date;
    private String booking_date;
    private String service_fee;
    private String surrogate_fee;
    private String total_fee;
    private String schedule_type;
    private String parent_name;
    private String surrogate_name;
    private String payment_status;

    // Constructor
    public InvoiceModal(String schedule_id, String schedule_date, String booking_date, String service_fee,
                        String surrogate_fee, String total_fee, String schedule_type, String parent_name,
                        String surrogate_name, String payment_status) {
        this.schedule_id = schedule_id;
        this.schedule_date = schedule_date;
        this.booking_date = booking_date;
        this.service_fee = service_fee;
        this.surrogate_fee = surrogate_fee;
        this.total_fee = total_fee;
        this.schedule_type = schedule_type;
        this.parent_name = parent_name;
        this.surrogate_name = surrogate_name;
        this.payment_status = payment_status;
    }

    // Getters
    public String getSchedule_id() {
        return schedule_id;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getService_fee() {
        return service_fee;
    }

    public String getSurrogate_fee() {
        return surrogate_fee;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getSchedule_type() {
        return schedule_type;
    }
    public String getParent_name() {
        return parent_name;
    }
    public String getSurrogate_name() {
        return surrogate_name;
    }
    public String getPayment_status() {
        return payment_status;
    }
}
