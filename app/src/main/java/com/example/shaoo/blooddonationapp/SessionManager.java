package com.example.shaoo.blooddonationapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "LoginInformation";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    //shared preference mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void setCredentials(String email, String password, String age, String gender, String latitude, String longitude, String name, String no, String city, String bg, String image) {
        editor.putString("Name", name);
        editor.putString("Number", no);
        editor.putString("City", city);
        editor.putString("BloodGroup", bg);
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("Age", age);
        editor.putString("Gender", gender);
        editor.putString("Longitude", longitude);
        editor.putString("Latitude", latitude);
        editor.putString("Image", image);
        editor.commit();
    }

    public String getName() {
        return pref.getString("Name", "");
    }

    public String getNumber() {
        return pref.getString("Number", "");
    }

    public String getCity() {
        return pref.getString("City", "");
    }

    public String getBG() {
        return pref.getString("BloodGroup", "");
    }

    public String getEmail() {
        return pref.getString("Email", "");
    }

    public String getPassword() {
        return pref.getString("Password", "");
    }

    public String getAge() {
        return pref.getString("Age", "");
    }

    public String getGender() {
        return pref.getString("Gender", "");
    }

    public String getLongi() {
        return pref.getString("Longitude", "");
    }

    public String getLatitude() {
        return pref.getString("Latitude", "");
    }

    public String getImage() {
        return pref.getString("Image", "");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
