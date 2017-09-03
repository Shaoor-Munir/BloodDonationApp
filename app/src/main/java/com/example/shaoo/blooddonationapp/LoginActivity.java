package com.example.shaoo.blooddonationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void OnClickSignUp(View view) {
        Toast.makeText(this,"Clicked sign up",Toast.LENGTH_SHORT).show();
    }

    public void OnClickForgotPassowrd(View view) {
        Toast.makeText(this,"Clicked forgot password",Toast.LENGTH_SHORT).show();
    }
}
