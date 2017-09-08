package com.example.shaoo.blooddonationapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getSimpleName();
    private EditText Name;
    private EditText Password;
    private EditText ContactNumber;
    private EditText EmailAddress;
    private EditText UserDescription;
    private EditText Date;
    private Spinner Gender;
    private Button RegisterButton;

    private String pattern = "dd-MM-yyyy";



    String NameInput;
    String PasswordInput;
    String ContactInput;
    String EmailInput;
    String UserDescriptionInput;
    String DateInput;
    String GenderInput;



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
        Date = (EditText) findViewById(R.id.appCompatEditText8);
        Gender = (Spinner) findViewById(R.id.spinner);
        RegisterButton = (Button) findViewById(R.id.appCompatButton2);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandlerClass(getApplicationContext());



        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NameInput = Name.getText().toString();
                PasswordInput = Password.getText().toString();
                ContactInput = ContactNumber.getText().toString();
                EmailInput = EmailAddress.getText().toString();
                UserDescriptionInput = UserDescription.getText().toString();
                DateInput = Date.getText().toString();
                GenderInput = Gender.getSelectedItem().toString();

                // checking if some field is emptyy or not
                // if yes then display the toast and return to app

                if(NameInput.matches("") || PasswordInput.matches("")
                        || ContactInput.matches("") ||
                        EmailInput.matches("") || UserDescriptionInput.matches("")
                        || DateInput.matches("")) {
                    Toast.makeText(getApplicationContext(), "SomeFieldMissing", Toast.LENGTH_LONG).show();
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
                String dateInString =new SimpleDateFormat(pattern).format(new Date());
                if(getCountOfDays(DateInput,dateInString) <18) {
                    Toast.makeText(getApplicationContext(), "For Signup you should be above 18", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_36dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked back button",Toast.LENGTH_SHORT).show();
            }
        });
    }


    // checking the email format if it is correct or not
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    // This function is used to get the count of days between the date of birth entered by the user
    // and the current date

    public int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null;
        java.util.Date expireCovertedDate = null;
        Date todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ((int) dayCount);
    }
}
