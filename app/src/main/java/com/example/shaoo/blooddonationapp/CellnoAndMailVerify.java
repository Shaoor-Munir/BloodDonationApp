package com.example.shaoo.blooddonationapp;

public class CellnoAndMailVerify {
    public static String CHECK_MAIL = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=CHECKEMAIL&EMAIL=";
    public static String CHECK_PHONE = "http://sundarsharif.com/onebloodAPIs/server/servercontroller.php?REQUEST_TYPE=CHECKPHONE&PHONE=";

    public static String LOGIN = "http://sundarsharif.com/onbloodtest/servercontroller.php?REQUEST_TYPE=LOGIN&";
    public static String SIGNUP = "http://sundarsharif.com/servers/oneblood/servercontroller.php";
    public static String UPDATE = "http://sundarsharif.com/servers/oneblood/servercontroller.php?REQUEST_TYPE=UPDATEUSERINFO&";
    public static String signup = "http://sundarsharif.com/servers/oneblood/servercontroller.php?REQUEST_TYPE=SIGNUP&NAME=myName&EMAIL=myEmail&PASSWORD=myPass&AGE=22&BLOODGROUP=ab%20&GENDER=1&CONTACT=%209232322323&CITY=Lahore&LONGI=12.2545&LATI=21.3542&AVAILABLE=1&IMAGE=aBase64String";

    public static String getUsersNearby(double lng, double lat, String bld) {
        return "http://sundarsharif.com/onbloodtest/servercontroller.php?REQUEST_TYPE=FINDBLOOD&BLOODG=" + bld + "&LAT1=" + lat + "&LON1=" + lng;
    }
    public String getEmailURL() {
        return CHECK_MAIL;
    }
    public String getNumberURL() {
       return CHECK_PHONE;
    }
    public String getloginUrl() {
        return LOGIN;
    }

    public String getUpdateURL() {
        return UPDATE;
    }
    //public String getSIGNUP() {return SIGNUP;}
}


