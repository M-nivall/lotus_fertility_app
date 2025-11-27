package com.example.Varsani.Staff.LabTech.Models;

public class FertilityTestModel {
    private String testId;
    private String scheduleId;
    private String surrogateId;
    private String parentId;
    private String testStatus;
    private String schedule_type;
    private String equipment_status;

    // Constructor
    public FertilityTestModel(String testId, String scheduleId, String surrogateId, String parentId,
                            String testStatus, String schedule_type, String equipment_status) {
        this.testId = testId;
        this.scheduleId = scheduleId;
        this.surrogateId = surrogateId;
        this.parentId = parentId;
        this.testStatus = testStatus;
        this.schedule_type = schedule_type;
        this.equipment_status = equipment_status;

    }

    public String getTestId() {
        return testId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public String getSurrogateId() {
        return surrogateId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getTestStatus() {
        return testStatus;
    }
    public String getSchedule_type() {
        return schedule_type;
    }
    public String getEquipment_status() {
        return equipment_status;
    }


}
