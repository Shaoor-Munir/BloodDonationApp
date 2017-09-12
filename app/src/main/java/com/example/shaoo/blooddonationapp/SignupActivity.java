package com.example.shaoo.blooddonationapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class SignupActivity extends AppCompatActivity {


    public static final String NO_REST = "MY";

    private JSONObject obj;
    private String  status;
    private ProgressDialog pDialog;
    private JSONParser jsonParser=new JSONParser();





    private EditText Name;
     private EditText Password;
     private EditText ContactNumber;
     private EditText EmailAddress;
     private EditText UserDescription;
     private EditText Date1;
     private Spinner Gender;
     private Button RegisterButton;

     private String NameInput;
     private String PasswordInput;
     private String ContactInput;
     private String EmailInput;
     private String UserDescriptionInput;
     private String DateInput;
     private String GenderInput;
     private String result1;

     private SessionManager session;
     private SQLiteHandlerClass db;

     private CellnoAndMailVerify ClassContainingUrls;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ClassContainingUrls = new CellnoAndMailVerify();

        Name = (EditText) findViewById(R.id.appCompatEditText2);
        Password = (EditText) findViewById(R.id.appCompatEditText3);
        ContactNumber = (EditText) findViewById(R.id.appCompatEditText4);
        EmailAddress = (EditText) findViewById(R.id.appCompatEditText5);
        UserDescription = (EditText) findViewById(R.id.appCompatEditText6);
        Date1 = (EditText) findViewById(R.id.dateofBirth);
        Gender = (Spinner) findViewById(R.id.spinner);
        RegisterButton = (Button) findViewById(R.id.appCompatButton2);
        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandlerClass(getApplicationContext());

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
                Toast.makeText(getApplicationContext(),"Clicked back button",Toast.LENGTH_SHORT).show();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 NameInput = Name.getText().toString().trim();
                 PasswordInput = Password.getText().toString().trim();
                 ContactInput = ContactNumber.getText().toString().trim();
                 EmailInput = EmailAddress.getText().toString().trim();
                 UserDescriptionInput = UserDescription.getText().toString().trim();
                 DateInput = Date1.getText().toString().trim();
                 GenderInput = Gender.getSelectedItem().toString().trim();
                 // checking if some field is empty or not
                 // if yes then display the toast and return to app
                 if(NameInput.matches("") || PasswordInput.matches("")
                         || ContactInput.matches("") ||
                         EmailInput.matches("") || UserDescriptionInput.matches("")
                         || DateInput.matches("")) {
                     Toast.makeText(getApplicationContext(), "Please Enter your Details", Toast.LENGTH_LONG).show();
                     return;
                 }
                 //Prompting the user to enter the correct email
                 if(isEmailValid(EmailInput) == false)
                 {
                     Toast.makeText(getApplicationContext(), "Please enter the correct email format", Toast.LENGTH_LONG).show();
                     return;
                 }

                 // Checking if the information typed by the user is above age 18
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
                     if(Integer.parseInt(dayDifference) < 2) {
                         Toast.makeText(getApplicationContext(), "For Signup you should be above 18", Toast.LENGTH_LONG).show();
                         return;
                     }

                 } catch (Exception exception) {
                     Log.e("DIDN'T WORK", "exception " + exception);
                 }


                 Log.i(NO_REST,"came out");

                 new CheckEmail().execute();






//                 Thread thread = new Thread(new Runnable() {
//
//                     @Override
//                     public void run() {
//                         String line = "";
//
//                         try{
//
//
//                             URL url = new URL(ClassContainingUrls.getEmailURL());
//                             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                             connection.setReadTimeout(10000);
//                             connection.setConnectTimeout(15000);
//                             connection.setRequestMethod("GET");
//                             connection.setDoInput(true);
//
//                             connection.connect();
//
//                             StringBuilder content = new StringBuilder();
//                             BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
//
//
//                             while( (line = reader.readLine()) != null ){
//                                 content.append(line);
//                             }
//                             line = content.toString();
//                             result1 = line;
//                         } catch(Exception ex) {
//                             line = ex.getMessage();
//                             ex.printStackTrace();
//                         }
//
//                     }
//                 });
//
//                thread.start();


//                 Log.i(NO_REST,result1);



                 //    try {
               //      obj = new JSONObject(result1);
                 //} catch (JSONException e) {
                   //  e.printStackTrace();
                 //}

                 //try {
                   //  status = obj.getString("STATUS");
                 //} catch (JSONException e) {
                   //  e.printStackTrace();
                // }
                 //Log.i(NO_REST,status);
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
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    Toast.makeText(SignupActivity.this, "Date picker clicked", Toast.LENGTH_SHORT);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                return false;
            }
        });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void load(){

        String line = "";

        try{

            URL url = new URL(ClassContainingUrls.getEmailURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();

            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );


            while( (line = reader.readLine()) != null ){
                content.append(line);
            }
            line = content.toString();
        } catch(Exception ex) {
            line = ex.getMessage();
            ex.printStackTrace();
        }

        result1 = line;
    }
    class CheckEmail extends AsyncTask<String, String, String> {

        String error=null;
        String status;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("Checking from server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON Object
            // Note that create product url accepts POST method

            JSONObject json = jsonParser.makeHttpRequest(ClassContainingUrls.getEmailURL()+EmailInput,
                    "POST", params);
            // checking for success tag
            try {
                if (json!=null) {

                    status = json.getString("STATUS");
                    //status= String.valueOf(statusObj);
                }
                else {
                    error="Please Connect Internet !";
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            Toast.makeText(SignupActivity.this, status,Toast.LENGTH_LONG).show();


        }

    }
}
