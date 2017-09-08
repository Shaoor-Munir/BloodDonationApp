package com.example.shaoo.blooddonationapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity implements OnFragmentInteractionListener {

    Toolbar toolbar;
    Fragment selectedFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = HomeFragment.newInstance(null, null);
                    break;
                case R.id.navigation_search:
                    selectedFragment = SearchFragment.newInstance(null, null);
                    break;
                case R.id.navigation_compatibility:
                    selectedFragment = CompatibilityFragment.newInstance(null, null);
                    break;
                case R.id.navigation_settings:
                    selectedFragment = SettingsFragment.newInstance(null, null);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_36dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked back button", Toast.LENGTH_SHORT).show();
            }
        });

        selectedFragment = HomeFragment.newInstance(null, null);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, selectedFragment);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void SearchClicked(View view) {

        selectedFragment = MapFragment.newInstance(null, null);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, selectedFragment);
        transaction.commit();
    }

    public void LikeClicked(View view) {
        Toast.makeText(this, "Like Clicked", Toast.LENGTH_SHORT).show();
    }

    public void ShareClicked(View view) {
        Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
    }
}
