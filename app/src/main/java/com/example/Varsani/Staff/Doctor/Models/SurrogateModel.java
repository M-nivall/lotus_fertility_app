package com.example.Varsani.Staff.Doctor.Models;

public class SurrogateModel {
    private String surrogateId;
    private String height;
    private String weight;
    private String bloodType;
    private String medication;
    private String maritalStatus;
    private String education;
    private String numChildren;
    private String moreDetails;
    private String idImage;
    private String medicalImage;
    private String photoImage;
    private String surrogateStatus;
    private String fullName;
    private String phoneNo;
    private String email;
    private String gender;
    private String dateBirth;
    private String county;
    private String role;

    // Constructor
    public SurrogateModel(String surrogateId, String height, String weight, String bloodType, String medication,
                          String maritalStatus, String education, String numChildren, String moreDetails,
                          String idImage, String medicalImage, String photoImage, String surrogateStatus,
                          String fullName, String phoneNo, String email, String gender, String dateBirth,
                          String county, String role) {
        this.surrogateId = surrogateId;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.medication = medication;
        this.maritalStatus = maritalStatus;
        this.education = education;
        this.numChildren = numChildren;
        this.moreDetails = moreDetails;
        this.idImage = idImage;
        this.medicalImage = medicalImage;
        this.photoImage = photoImage;
        this.surrogateStatus = surrogateStatus;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.county = county;
        this.role=role;
    }

    // Getters
    public String getSurrogateId() {
        return surrogateId;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getMedication() {
        return medication;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public String getNumChildren() {
        return numChildren;
    }

    public String getMoreDetails() {
        return moreDetails;
    }

    public String getIdImage() {
        return idImage;
    }

    public String getMedicalImage() {
        return medicalImage;
    }

    public String getPhotoImage() {
        return photoImage;
    }

    public String getSurrogateStatus() {
        return surrogateStatus;
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

    public String getGender() {
        return gender;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public String getCounty() {
        return county;
    }
    public String getRole() {
        return role;
    }
}
