package com.example.shaoo.blooddonationapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.security.AccessController.getContext;


public class SignupActivity extends AppCompatActivity implements android.location.LocationListener {

    public static final String NO_REST = "MY";
    String age;
    String img_str;
    String RequiredGender;
    LocationListener mLocationListener;
    Location location;
    double longitude;
    double latitude;
    LocationManager mLocationManager;
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    private EditText Name;
    private EditText EmailAddress;
    private EditText Password;
    private EditText ContactNumber;
    private EditText City;
    private EditText Date1; //Date of birth
    private Spinner BloodGroup;
    private Spinner Gender;
    private Button RegisterButton;
    private String NameInput;
    private String EmailInput;
    private String PasswordInput;
    private String ContactInput;
    private String CityInput;
    private String DateInput;
    private String GenderInput;
    private String BloodGroupInput;
    //for the image manipulation
    private ImageView img;
    private SessionManager session;
    private CellnoAndMailVerify ClassContainingUrls;
    // variables related to the location thingy
    private LocationManager locationManager;
    private String provider;
    private String Longitude;
    private String Latitude;
    private JSONObject jsonObj;

    public static boolean isInternetConnected(Context ctx) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi != null) {
            if (wifi.isConnected()) {
                return true;
            }
        }
        if (mobile != null) {
            if (mobile.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ClassContainingUrls = new CellnoAndMailVerify();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(provider, 400, 1, this);
        location = getLastKnownLocationCustom();


        Name = (EditText) findViewById(R.id.appCompatEditText2);
        Password = (EditText) findViewById(R.id.appCompatEditText3);
        ContactNumber = (EditText) findViewById(R.id.appCompatEditText4);
        EmailAddress = (EditText) findViewById(R.id.appCompatEditText5);
        City = (EditText) findViewById(R.id.appCompatEditText6);
        Date1 = (EditText) findViewById(R.id.dateofBirth);
        Gender = (Spinner) findViewById(R.id.genderSpinner);
        BloodGroup = (Spinner) findViewById(R.id.bloodSpinner);
        RegisterButton = (Button) findViewById(R.id.appCompatButton2);
        img = (ImageView) findViewById(R.id.Profile);


        // Session manager
        session = new SessionManager(getApplicationContext());

        // creating the JSON object
        jsonObj = new JSONObject();

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignupActivity.this,
                    HomeScreen.class);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_36dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        final EditText txtDate = (EditText) findViewById(R.id.dateofBirth);
        txtDate.setTag(txtDate.getKeyListener());
        txtDate.setKeyListener(null);

        txtDate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            Toast.makeText(SignupActivity.this, "Date picker clicked", Toast.LENGTH_SHORT);
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                return false;
            }
        });
    }

    public void CheckEverything(View v) throws InterruptedException {
        NameInput = Name.getText().toString().trim();
        PasswordInput = Password.getText().toString().trim();
        ContactInput = ContactNumber.getText().toString().trim();
        EmailInput = EmailAddress.getText().toString().trim();
        CityInput = City.getText().toString().trim();
        DateInput = Date1.getText().toString().trim();
        GenderInput = Gender.getSelectedItem().toString().trim();
        BloodGroupInput = BloodGroup.getSelectedItem().toString().trim();


        Bitmap image = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        img_str = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


        //Converting the image to the bitmap image
//        img.buildDrawingCache();
//        Bitmap bitmap = img.getDrawingCache();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
//        final byte[] image = stream.toByteArray();
//        img_str = Base64.encodeToString(image, 0);

        // checking if some field is empty or not
        // if yes then display the toast and return to app
        if (NameInput.matches("") || PasswordInput.matches("")
                || ContactInput.matches("") ||
                EmailInput.matches("") || CityInput.matches("")
                || DateInput.matches("")) {
            Toast.makeText(getApplicationContext(), "Please Enter your Details", Toast.LENGTH_LONG).show();
            return;
        }


        //Prompting the user to enter the correct email
        if (isEmailValid(EmailInput) == false) {
            Toast.makeText(getApplicationContext(), "Please enter the correct email format", Toast.LENGTH_LONG).show();
            return;
        }


        // Checking if the user is above 18
        // by subtracting the current date from the date of birth entered by the user
        // utility function is at the end of the file

        String currentDate = getDateTime();
        try {
            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");

            //Setting dates
            date1 = dates.parse(currentDate);
            date2 = dates.parse(DateInput);

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);

            //Convert long to String
            String dayDifference = Long.toString(differenceDates);
            int year;
            int month;
            int days;
            int weeks;
            year = 0;
            month = 0;
            days = 0;
            weeks = 0;

            for (int i = 0; i < Integer.parseInt(dayDifference); i++) {
                if ((i % 30) == 0)
                    month++;
                if ((i % 7) == 0)
                    weeks++;
                if ((i % 365) == 0)
                    year++;
                if ((i % 1) == 0)
                    days++;
            }


            if (year < 18) {
                Toast.makeText(getApplicationContext(), "For Signup you should be above 18", Toast.LENGTH_LONG).show();
                return;
            } else {
                age = Integer.toString(year);
            }

        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }


        // Checking the internet connection
        if (isNetworkAvailable() == false) {
            Toast.makeText(SignupActivity.this, "Please switched on the network", Toast.LENGTH_LONG).show();
            return;
        }

        // checking if the email already exists or not
        String url = ClassContainingUrls.getEmailURL() + EmailInput;



        // Sending the request via the volley
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("STATUS") == "SUCCESS") {
                                Toast.makeText(getApplicationContext(), "Email Already taken.Please enter a different email", Toast.LENGTH_LONG).show();
                                return;
                            } else {

                                String url1 = ClassContainingUrls.getNumberURL() + ContactInput;
                                checkLocation();
                                RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                                JsonObjectRequest req1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {

                                                    if (response.getString("STATUS") == "SUCCESS") {
                                                        Toast.makeText(getApplicationContext(), "Phone no already " +
                                                                "taken.Please Enter different", Toast.LENGTH_LONG).show();
                                                        return;
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Number and mail not existing", Toast.LENGTH_LONG).show();
                                                        if (ActivityCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                                            return;
                                                        }
                                                        locationManager.requestLocationUpdates(provider, 400, 1, SignupActivity.this);
                                                        location = getLastKnownLocationCustom();

                                                        if (location != null) {
                                                            //locationManager.requestLocationUpdates(provider, 400, 1,SignupActivity.this);

                                                            locationManager.requestLocationUpdates(provider, 400, 1, SignupActivity.this);
                                                            location = getLastKnownLocationCustom();
                                                            DecimalFormat df = new DecimalFormat("##.####");
                                                            Latitude = df.format(location.getLatitude());
                                                            Longitude = df.format(location.getLongitude());
                                                            RequiredGender = "";
                                                            if (GenderInput == "Male") {
                                                                Log.i(NO_REST, "Zaroor aya 1");
                                                                RequiredGender = "1";

                                                            } else if (GenderInput == "Female") {
                                                                Log.i(NO_REST, "Zaroor aya 2");
                                                                RequiredGender = "0";
                                                            }

                                                            // now here sending all the input data to the api
                                                            // no need to save in the database as it won't be needed in future
                                                            StringRequest postRequest = new StringRequest(Request.Method.POST, "http://sundarsharif.com/servers/oneblood/servercontroller.php",
                                                                    new Response.Listener<String>()
                                                                    {
                                                                        @Override
                                                                        public void onResponse(String response) {

                                                                            // Checking if the string contains Status =  Success
                                                                            if(response.contains("SUCCESS")){


                                                                                session.setLogin(true);
                                                                                Intent intent = new Intent(SignupActivity.this,HomeScreen.class);
                                                                                startActivity(intent);
                                                                            }
                                                                            else {
                                                                                Toast.makeText(SignupActivity.this,"Response returns Failure",Toast.LENGTH_LONG).show();
                                                                            }

                                                                        }
                                                                    },
                                                                    new Response.ErrorListener()
                                                                    {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            Toast.makeText(SignupActivity.this,"Again messed up",Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                            ) {
                                                                @Override
                                                                protected Map<String, String> getParams()
                                                                {
                                                                    Map<String, String> params = new HashMap<String, String>();
                                                                    params.put("REQUEST_TYPE", "SIGNUP");
                                                                    params.put("NAME", NameInput);
                                                                    params.put("EMAIL", EmailInput);
                                                                    params.put("PASSWORD", PasswordInput);
                                                                    params.put("AGE", RequiredGender);
                                                                    params.put("BLOODGROUP", BloodGroupInput);
                                                                    params.put("GENDER", GenderInput);
                                                                    params.put("CONTACT", ContactInput);
                                                                    params.put("CITY", CityInput);
                                                                    params.put("LONGI", Longitude);
                                                                    params.put("LATI", Latitude);
                                                                    params.put("AVAILABLE", "1");
                                                                    params.put("IMAGE", "noimage");
                                                                    return params;
                                                                }
                                                            };
                                                            Volley.newRequestQueue(SignupActivity.this).add(postRequest);

                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Location is null", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error Occured while sending the data1", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                });
                                queue1.add(req1);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Occured while sending the data2", Toast.LENGTH_LONG).show();
                return;
            }
        });
        queue.add(req);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void checkLocation() {

        String serviceString = Context.LOCATION_SERVICE;
        mLocationManager = (LocationManager) getSystemService(serviceString);


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        mLocationListener = new android.location.LocationListener() {
            public void onLocationChanged(Location locationListener) {

                if (isGPSEnabled(getApplicationContext())) {
                    if (locationListener != null) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        if (mLocationManager != null) {
                            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Turn On GPS First!", Toast.LENGTH_SHORT).show();
                    if (isInternetConnected(getApplicationContext())) {
                        if (mLocationManager != null) {
                            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }


            public void onProviderDisabled(String provider) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
    }

    public boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void LoadImage(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                img.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(SignupActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    public Location getLastKnownLocationCustom() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(SignupActivity.NO_REST, "SENDING NULL TO LOCATION");
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
