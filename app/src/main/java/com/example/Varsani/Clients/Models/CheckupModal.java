package com.example.Varsani.Clients.Models;

public class CheckupModal {
    private String checkID, scheduleID, parentID, first_check;
    private String second_check, delivery_check, check_status;

    public CheckupModal(String checkID, String scheduleID, String parentID, String first_check,
                          String second_check, String delivery_check, String check_status) {
        this.checkID = checkID;
        this.scheduleID = scheduleID;
        this.parentID = parentID;
        this.first_check = first_check;
        this.second_check = second_check;
        this.delivery_check = delivery_check;
        this.check_status = check_status;
    }

    // Getter methods
    public String getCheckID() { return checkID; }
    public String getScheduleID() { return scheduleID; }
    public String getParentID() { return parentID; }
    public String getFirst_check() { return first_check; }
    public String getSecond_check() { return second_check; }
    public String getDelivery_check() { return delivery_check; }
    public String getCheck_status() { return check_status; }
}
