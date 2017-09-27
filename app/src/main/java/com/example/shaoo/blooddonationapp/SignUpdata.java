package com.example.shaoo.blooddonationapp;


public class SignUpdata {
    private String Name;
    private String Password;
    private String Contactno;
    private String Email;
    private String userDescription;
    private String bloodGroup;
    private String dateofbirth;
    private String Gender;

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    private String Longitude;
    private String Latitude;

    public String getImageinbase64() {
        return Imageinbase64;
    }

    public void setImageinbase64(String imageinbase64) {
        Imageinbase64 = imageinbase64;
    }

    private String Imageinbase64;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getContactno() {
        return Contactno;
    }

    public void setContactno(String contactno) {
        Contactno = contactno;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
