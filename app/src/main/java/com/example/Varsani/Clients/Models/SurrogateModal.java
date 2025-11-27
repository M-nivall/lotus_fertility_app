package com.example.Varsani.Clients.Models;

public class SurrogateModal {
    private String surrogateId;
    private String height;
    private String bloodType;
    private String photoUrl;
    private String fullName;
    private String weight;
    private String education;
    private  String fee;
    private  String town;
    private  String county;
    private  String age;
    private String role;



    public SurrogateModal(String surrogateId, String height, String bloodType, String photoUrl, String fullName,
                          String weight, String education, String fee, String town, String county, String age, String role) {
        this.surrogateId = surrogateId;
        this.height = height;
        this.bloodType = bloodType;
        this.photoUrl = photoUrl;
        this.fullName = fullName;
        this.weight = weight;
        this.education = education;
        this.fee = fee;
        this.town = town;
        this.county = county;
        this.age = age;
        this.role = role;
    }

    public String getSurrogateId() {
        return surrogateId;
    }

    public String getHeight() {
        return height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getFullName() {
        return fullName;
    }
    public String getWeight() {
        return weight;
    }
    public String getEducation() {
        return education;
    }
    public String getFee() {
        return fee;
    }
    public String getTown() {
        return town;
    }
    public String getCounty() {
        return county;
    }
    public String getAge() {
        return age;
    }
    public String getRole() {
        return role;
    }

}
