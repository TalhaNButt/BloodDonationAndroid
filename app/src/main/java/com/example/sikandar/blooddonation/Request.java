package com.example.sikandar.blooddonation;

public class Request {
    private String id;
    private String title;
    private String Description;
    private String bloodgroup;
    private String currentdate;
    private String currenttime;
    private String userid;
    private String name;
    private String contact;
    private String userbloodgroup;
    private double lat;
    private double lon;

    public Request(String title, String description, String bloodgroup, double lat, double lon ) {
        this.title = title;
        Description = description;
        this.bloodgroup = bloodgroup;
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.contact = contact;
        this.userbloodgroup = userbloodgroup;
        this.lat = lat;
        this.lon = lon;
    }

    public Request(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserbloodgroup() {
        return userbloodgroup;
    }

    public void setUserbloodgroup(String userbloodgroup) {
        this.userbloodgroup = userbloodgroup;
    }
}
