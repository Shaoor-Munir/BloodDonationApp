package com.example.shaoo.blooddonationapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

    }

    public void LoginButtonPressed(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void SignupButtonPressed(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void SearchClicked(View view) {
        Toast.makeText(this, "This is currently under construction", Toast.LENGTH_SHORT).show();
    }
}
