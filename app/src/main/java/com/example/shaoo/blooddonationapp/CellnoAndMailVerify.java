package com.example.shaoo.blooddonationapp;

public class CellnoAndMailVerify {
    public static String CHECK_MAIL = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=CHECKEMAIL&EMAIL=myemail";
    public static String CHECK_LOGIN = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=CHECKPHONE&PHONE=";
    public static String LOGIN = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=LOGIN&EMAIL=myEmail&PASSWORD=myPass";
    public static String SIGNUP = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=SIGNUP&NAME=myName&EMAIL=myEmail&PASSWORD=myPass&AGE=22&BLOODGROUP=ab+&GENDER=1&CONTACT=+9232322323&CITY=Lahore&LONGI=12.2545&LATI=21.3542&AVAILABLE=1";

    public String appendPhoneNumber (String cellNumber) {
        return CHECK_LOGIN + cellNumber;
    }
    public String getEmailURL() {
        return CHECK_MAIL;
    }



}
