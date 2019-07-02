package com.example.sikandar.blooddonation;

import android.text.Editable;

import java.io.Serializable;

public class User implements Serializable {
    public String name, phone, bloodgroup, username, gender, availability, id, status;
    public String placeName;
    double lon, lat;


    public User(){

    }

    public User(String id, String name, String username,  String phone, String bloodgroup, String gender, double lon, double lat,String placeName, String availability, String status) {
        this.id=id;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.bloodgroup = bloodgroup;
        this.gender = gender;
        this.lon = lon;
        this.lat = lat;
        this.placeName = placeName;
        this.availability = availability;
        this.status=status;
    }

    public CharSequence getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;

    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}