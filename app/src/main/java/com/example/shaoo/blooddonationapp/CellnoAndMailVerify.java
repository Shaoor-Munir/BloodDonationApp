package com.example.shaoo.blooddonationapp;

public class CellnoAndMailVerify {
    public static String CHECK_MAIL = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=CHECKEMAIL&EMAIL=";
    public static String CHECK_PHONE = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=CHECKPHONE&PHONE=";

    public static String LOGIN = "http://sundarsharif.com/onbloodtest/servercontroller.php?REQUEST_TYPE=LOGIN&EMAIL=myEmail1&PASSWORD=mypass";
    public static String SIGNUP = "http://sundarsharif.com/onbloodtest/servercontroller.php?REQUEST_TYPE=SIGNUP&NAME=myName&EMAIL=myEmail&PASSWORD=myPass&AGE=22&BLOODGROUP=ab+&GENDER=1&CONTACT=+9232322323&CITY=Lahore&LONGI=12.2545&LATI=21.3542&AVAILABLE=1&IMAGE=aBase64String";

    public static String USERS_NEARBY = "http://sundarsharif.com/onbloodtest/servercontroller.php?REQUEST_TYPE=FINDBLOOD&BLOODG=B&LAT1=20&LON1=10";


    public String getEmailURL() {
        return CHECK_MAIL;
    }
    public String getNumberURL() {
       return CHECK_PHONE;
    }

    //EMAIL=myEmail&PASSWORD=myPass

}
