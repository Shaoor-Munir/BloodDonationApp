package com.example.shaoo.blooddonationapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    private String NumberResult;
    private String EmailResult;






     private EditText Name;
     private EditText Password;
     private EditText ContactNumber;
     private EditText EmailAddress;
     private EditText UserDescription;
     private EditText Date1;
     private Spinner Gender;
     private Spinner BloodGroup;
     private Button RegisterButton;

     private String NameInput;
     private String PasswordInput;
     private String ContactInput;
     private String EmailInput;
     private String UserDescriptionInput;
     private String DateInput;
     private String GenderInput;
     private String BloodGroupInput;
     private ImageView img;
     String img_str;


    private String result1;
     CheckEmail check1;
     CheckNumber check2;
     private SignUpdata sigupData;

     private SessionManager session;
     private SQLiteHandlerClass db;

     private CellnoAndMailVerify ClassContainingUrls;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sigupData = new SignUpdata();

        ClassContainingUrls = new CellnoAndMailVerify();

        Name = (EditText) findViewById(R.id.appCompatEditText2);
        Password = (EditText) findViewById(R.id.appCompatEditText3);
        ContactNumber = (EditText) findViewById(R.id.appCompatEditText4);
        EmailAddress = (EditText) findViewById(R.id.appCompatEditText5);
        UserDescription = (EditText) findViewById(R.id.appCompatEditText6);
        Date1 = (EditText) findViewById(R.id.dateofBirth);
        Gender = (Spinner) findViewById(R.id.spinner);
        BloodGroup = (Spinner) findViewById(R.id.appCompatSpinner);
        RegisterButton = (Button) findViewById(R.id.appCompatButton2);
        img = (ImageView) findViewById(R.id.Profile);
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
                 BloodGroupInput = BloodGroup.getSelectedItem().toString().trim();


                //Converting the image to the bitmap image
                 Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.id.Profile);
                 ByteArrayOutputStream stream=new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                 byte[] imageByteArray=stream.toByteArray();
                 img_str = Base64.encodeToString(imageByteArray, 0);

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
                     if(Integer.parseInt(dayDifference) < 2) {
                         Toast.makeText(getApplicationContext(), "For Signup you should be above 18", Toast.LENGTH_LONG).show();
                         return;
                     }

                 } catch (Exception exception) {
                     Log.e("DIDN'T WORK", "exception " + exception);
                 }

                 //This is an async task to check if the email entered by the user
                 // is already present or not

                 check1 = (CheckEmail) new CheckEmail();
                 check2 = (CheckNumber) new CheckNumber();



                 check1.execute();
                 if(check1.getStatus2() == "SUCCESS") {
                     Toast.makeText(SignupActivity.this, "Email Already taken Please enter a different Email",Toast.LENGTH_LONG).show();
                     return;
                 }
                 else {
                     check2.execute();
                     if(check2.getStatus1() == "SUCCESS") {
                         Toast.makeText(SignupActivity.this, "Phone Already taken Please enter a different phone number",Toast.LENGTH_LONG).show();
                         return;
                     }
                 }

                 if(check1.getStatus2() == "FAIL")
                 {
                     if(check2.getStatus1() == "FAIL"){



                         //Checking if location is enabled
                         LocationManager lm = (LocationManager)getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
                         boolean gps_enabled = false;
                         boolean network_enabled = false;

                         try {
                             gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                         } catch(Exception ex) {}

                         try {
                             network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                         } catch(Exception ex) {}

                         if(!gps_enabled && !network_enabled) {
                             // notify user
                             AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
                             dialog.setMessage(getApplicationContext().getResources().getString(R.string.gps_network_not_enabled));
                             dialog.setPositiveButton(getApplicationContext().getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                     // TODO Auto-generated method stub
                                     Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                     getApplicationContext().startActivity(myIntent);
                                     //get gps
                                 }
                             });
                             dialog.setNegativeButton(getApplicationContext().getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                                 @Override
                                 public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                     // TODO Auto-generated method stub
                                 }
                             });
                             dialog.show();
                         }











                         sigupData.setName(NameInput);
                         sigupData.setContactno(ContactInput);
                         sigupData.setDateofbirth(DateInput);
                         sigupData.setPassword(PasswordInput);
                         sigupData.setEmail(EmailInput);
                         sigupData.setUserDescription(UserDescriptionInput);
                         sigupData.setGender(GenderInput);
                         sigupData.setBloodGroup(BloodGroupInput);
                         sigupData.setImageinbase64(img_str);
                         register_User(sigupData);
                     }
                 }
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

        }else {
            Toast.makeText(SignupActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    private void register_User(SignUpdata obj) {






    }
    class SendingSignUpCredentials extends AsyncTask<String,String,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    //This async task is for verifying the number if it already exists or not

    class CheckNumber extends AsyncTask<String,String,String>{
        String error=null;
        String status1;


        public String getError() {
            return error;
        }

        public String getStatus1() {
            return status1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }


        @Override
        protected String doInBackground(String... strings) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON Object
            // Note that create product url accepts POST method

            JSONObject json = jsonParser.makeHttpRequest(ClassContainingUrls.getNumberURL()+ContactInput,
                    "POST", params);
            // checking for success tag
            try {
                if (json!=null) {

                    status1 = json.getString("STATUS");
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


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // dismiss the dialog once done
            pDialog.dismiss();
            //if(status1 == "SUCCESS"){
              //  Toast.makeText(SignupActivity.this, "Number Already taken",Toast.LENGTH_LONG).show();
            //}
        }
    }


    class CheckEmail extends AsyncTask<String, String, String> {

        String error=null;
        String status2;


        public String getError() {
            return error;
        }

        public String getStatus2() {
            return status2;
        }


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("Verifying email");
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

                    status2 = json.getString("STATUS");
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
            //if(status == "SUCCESS"){
              //  Toast.makeText(SignupActivity.this, "Email Already taken",Toast.LENGTH_LONG).show();
            //}
        }
    }
}
