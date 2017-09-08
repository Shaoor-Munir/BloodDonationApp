package com.example.shaoo.blooddonationapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

     private EditText Name;
     private EditText Password;
     private EditText ContactNumber;
     private EditText EmailAddress;
     private EditText UserDescription;
     private EditText Date;
     private Spinner Gender;
     private Button RegisterButton;

     private String NameInput;
     private String PasswordInput;
     private String ContactInput;
     private String EmailInput;
     private String UserDescriptionInput;
     private String DateInput;
     private String GenderInput;

     private SessionManager session;
     private SQLiteHandlerClass db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = (EditText) findViewById(R.id.appCompatEditText2);
        Password = (EditText) findViewById(R.id.appCompatEditText3);
        ContactNumber = (EditText) findViewById(R.id.appCompatEditText4);
        EmailAddress = (EditText) findViewById(R.id.appCompatEditText5);
        UserDescription = (EditText) findViewById(R.id.appCompatEditText6);
        Date = (EditText) findViewById(R.id.dateofBirth);
        Gender = (Spinner) findViewById(R.id.spinner);
        RegisterButton = (Button) findViewById(R.id.appCompatButton2);
        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandlerClass(getApplicationContext());




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
                 DateInput = Date.getText().toString().trim();
                 GenderInput = Gender.getSelectedItem().toString().trim();
                 // checking if some field is emptyy or not
                 // if yes then display the toast and return to app
                 if(NameInput.matches("") || PasswordInput.matches("")
                         || ContactInput.matches("") ||
                         EmailInput.matches("") || UserDescriptionInput.matches("")
                         || DateInput.matches("")) {
                     Toast.makeText(getApplicationContext(), "SomeFieldsMissing", Toast.LENGTH_LONG).show();
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






}
