package com.revolt.attendancemanager;


public class User {

    String name;
    String ph;
    String place;
    String email;

    public User() {
    }

    public User(String name, String ph, String place, String email) {
        this.name = name;
        this.ph = ph;
        this.place = place;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
