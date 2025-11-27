package com.example.Varsani.Staff.Doctor.Models;

public class CheckupModel {

    private String  checkupID;
    private String scheduleID;
    private String surrogateID;
    private String parentID;
    private String surrogateName;
    private String user;
    private String first_check;
    private String second_check;
    private String delivery_check;

    public CheckupModel(String checkupID, String scheduleID, String surrogateID, String parentID, String surrogateName,
                        String user, String first_check, String second_check, String delivery_check) {
        this.checkupID =checkupID;
        this.scheduleID = scheduleID;
        this.surrogateID = surrogateID;
        this.parentID = parentID;
        this.surrogateName = surrogateName;
        this.user = user;
        this.first_check = first_check;
        this.second_check = second_check;
        this.delivery_check = delivery_check;
    }

    public String getCheckupID() {
        return checkupID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public String getSurrogateID() {
        return surrogateID;
    }
    public String getParentID() {
        return parentID;
    }
    public String getSurrogateName() {
        return surrogateName;
    }
    public String getUser() {
        return user;
    }
    public String getFirst_check() {
        return first_check;
    }
    public String getSecond_check() {
        return second_check;
    }
    public String getDelivery_check() {
        return delivery_check;
    }
}
