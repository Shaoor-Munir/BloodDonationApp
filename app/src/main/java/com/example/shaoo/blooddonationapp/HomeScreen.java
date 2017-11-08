package com.example.shaoo.blooddonationapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeScreen extends AppCompatActivity implements OnFragmentInteractionListener {



    Toolbar toolbar;
    Fragment selectedFragment;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            String tag = "";
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tag = "home";
                    selectedFragment = fm.findFragmentByTag(tag);
                    if (selectedFragment == null)
                        selectedFragment = HomeFragment.newInstance(null, null);
                    break;
                case R.id.navigation_search:
                    tag = "search";
                    selectedFragment = fm.findFragmentByTag(tag);
                    if (selectedFragment == null)
                        selectedFragment = SearchFragment.newInstance(null, "Home");
                    break;
                case R.id.navigation_compatibility:
                    tag = "compatibility";
                    selectedFragment = fm.findFragmentByTag(tag);
                    if (selectedFragment == null)
                        selectedFragment = CompatibilityFragment.newInstance(null, null);
                    break;
                case R.id.navigation_settings:
                    tag = "settings";
                    selectedFragment = fm.findFragmentByTag(tag);
                    if (selectedFragment == null)
                        selectedFragment = SettingsFragment.newInstance(null, null);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment, tag);
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

    public void LikeClicked(View view) {
        Toast.makeText(this, "Like Clicked", Toast.LENGTH_SHORT).show();
    }

    public void ShareClicked(View view) {
        Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
    }
}
