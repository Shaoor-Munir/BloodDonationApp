package com.example.shaoo.blooddonationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private final String APP = "MY1";
    Intent i;
    private EditText email;
    private EditText password;
    private JSONParser jsonParser=new JSONParser();
    private ProgressDialog pDialog;
    private SessionManager session;
    private CellnoAndMailVerify ur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // getting the variables from the input
        email = (EditText) findViewById(R.id.emailI);
        password = (EditText) findViewById(R.id.passwordI);
        email.setText("jamshaidsohail@gmail.com");
        password.setText("91799674");
        ur = new CellnoAndMailVerify();



        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);




        // Session manager
        session = new SessionManager(getApplicationContext());



        // checking if user is already looged in
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
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
    }

    public void OnClickSignUp(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void OnClickForgotPassowrd(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void LoginPressed(View view) {

        // checking if internet is available
        if (isNetworkAvailable() == false){
            Toast.makeText(getApplicationContext(),"Please connect to the internet",Toast.LENGTH_LONG).show();
            return;
        }

        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (!em.isEmpty() && !pass.isEmpty()) {

            String url = ur.getloginUrl()+"EMAIL="+em+"&PASSWORD="+pass;


            RequestQueue queue = Volley.newRequestQueue(this);
            final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                if(response.getString("STATUS") == "FAIL") {
                                    Toast.makeText(getApplicationContext(),"Login Information incorrect! Please try again!",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else {

                                    String name = response.getString("name");
                                    String number = response.getString("contact");
                                    String city = response.getString("city");
                                    String bloodgroup = response.getString("bloodGroup");
                                    String email = response.getString("email");
                                    String password = response.getString("password");
                                    String age = response.getString("age");
                                    String gender = response.getString("gender");
                                    String longitude = response.getString("longi");
                                    String latitude = response.getString("lati");
                                    String image = response.getString("image");
                                    if (bloodgroup.equals("Mal"))
                                        bloodgroup = "BP";
                                    session.setCredentials(email, password, age, gender, latitude, longitude, name, number, city, bloodgroup, image);
                                    session.setLogin(true);
                                    i = new Intent(getApplicationContext(),HomeScreen.class);
                                    startActivity(i);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error Occured while sending the data",Toast.LENGTH_LONG).show();
                }
            });
            queue.add(req);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
