package com.example.shaoo.blooddonationapp;

/**
 * Created by Aqsa Sohail on 10/7/2017.
 */

public class LoginData {
    private String Status;
    private String message;
    private String ID;

    public void setStatus(String status) {
        Status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setAvailable(String available) {
        Available = available;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setConclusion(boolean conclusion) {
        this.conclusion = conclusion;
    }

    private String Mail;
    private String password;
    private String Name;
    private String age;
    private String bloodgroup;
    private String gender;
    private String Contact;
    private String city;
    private String longitude;
    private String latitude;
    private String Available;
    private String image;
    private boolean conclusion;



    public String getContact() {
        return Contact;
    }

    public boolean isConclusion() {
        return conclusion;
    }

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return message;
    }

    public String getID() {
        return ID;
    }

    public String getMail() {
        return Mail;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return Name;
    }

    public String getAge() {
        return age;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAvailable() {
        return Available;
    }

    public String getImage() {
        return image;
    }

}
