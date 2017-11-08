package com.example.shaoo.blooddonationapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.HashMap;


//This database is used when we have to fetch the login information
//Instead of making request to the server we fetch it from the database


public class SQLiteHandlerClass extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandlerClass.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;



    // Database Name
    private static final String DATABASE_NAME = "LoginInformation";

    // Login table name
    private static final String TABLE_USER = "UserData";

    //These are the columns of the table
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_IMAGE = "image";
    private static final String BLOOD_GROUP = "blood_group";
    private static final String KEY_CITY = "city";
    private static final String KEY_ID = "id";



    public SQLiteHandlerClass(Context context) {
        super(context,TABLE_USER, null, DATABASE_VERSION);
    }

    public SQLiteHandlerClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER +
//                "(" + KEY_NAME + " TEXT,"
//                + KEY_EMAIL + " TEXT UNINQUE,"
//                + KEY_PASSWORD + " TEXT,"
//                + KEY_CITY + " TEXT,"
//                + KEY_CONTACT + " TEXT UNIQUE,"
//                + KEY_AGE + " INTEGER,"
//                + KEY_GENDER + " INTEGER,"
//                + KEY_LONGITUDE + " REAL,"
//                + KEY_LATITUDE + " REAL,"
//                + KEY_IMAGE + " VARCHAR(100),"
//                + BLOOD_GROUP + " TEXT," + ")";


        db.execSQL(" CREATE TABLE " + TABLE_USER + " (" +
                KEY_ID + " TEXT PRIMARY KEY, " +
                KEY_NAME + " TEXT UNIQUE, " +
                KEY_EMAIL + " TEXT UNIQUE, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_CITY + " TEXT, " +
                KEY_CONTACT + " TEXT, " +
                KEY_GENDER + " TEXT, " +
                KEY_LONGITUDE + " TEXT, " +
                KEY_LATITUDE + " TEXT, " +
                KEY_IMAGE + " VARCHAR(100), " +
                BLOOD_GROUP + " TEXT, " +
                KEY_AGE + " TEXT);"
        );


        //db.execSQL(CREATE_LOGIN_TABLE);

        Log.i(TAG, "Database table named UserData created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }


    public void add_user_data(LoginData data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, data.getName());
        values.put(KEY_EMAIL, data.getMail());
        values.put(KEY_PASSWORD, data.getPassword());
        values.put(KEY_CITY, data.getCity());
        values.put(KEY_CONTACT,data.getContact());
        values.put(KEY_AGE,data.getAge());
        values.put(KEY_GENDER,data.getGender());
        values.put(KEY_LONGITUDE,data.getLongitude());
        values.put(KEY_LATITUDE,data.getLatitude());
        values.put(BLOOD_GROUP,data.getBloodgroup());

        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);


    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("email", cursor.getString(1));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
