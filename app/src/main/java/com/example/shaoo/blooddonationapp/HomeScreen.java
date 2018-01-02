package com.example.shaoo.blooddonationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity implements OnFragmentInteractionListener {



    Toolbar toolbar;
    Fragment selectedFragment;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    SessionManager sessionManager;
    CellnoAndMailVerify urls;
    int countBack = 0;
    CircleImageView profile;
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

        sessionManager = new SessionManager(HomeScreen.this);
        urls = new CellnoAndMailVerify();
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_36dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this).setMessage("Do you want to close this Application?")
                        .setTitle("Exit")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
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

    public void EditProfile(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText name = (EditText) dialogView.findViewById(R.id.EditNameField);
        final EditText city = (EditText) dialogView.findViewById(R.id.EditCityField);
        final EditText number = (EditText) dialogView.findViewById(R.id.EditNumberField);
        final Spinner bloodType = (Spinner) dialogView.findViewById(R.id.EditBloodType);

        name.setText(sessionManager.getName());
        city.setText(sessionManager.getCity());
        number.setText(sessionManager.getNumber());
        final String bloodGroup[] = getResources().getStringArray(R.array.bloodgrp_dropdown_arrays);
        String BGroup = sessionManager.getBG();
        char c = BGroup.charAt(BGroup.length() - 1);
        if (c == 'P')
            BGroup = BGroup.replace("P", "+");
        else
            BGroup = BGroup.replace("N", "-");
        int index = 0;
        for (String bg : bloodGroup) {
            if (bg.equals(BGroup))
                break;
            index++;
        }
        bloodType.setSelection(index);
        dialogBuilder.setTitle("Edit Profile");
        dialogBuilder.setMessage("Update information below");

        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String update_url = urls.getUpdateURL() +
                        "EMAIL=" + sessionManager.getEmail() +
                        "&AGE=" + sessionManager.getAge() +
                        "&CITY=" + sessionManager.getCity() +
                        "&NAME=" + sessionManager.getName() +
                        "&CONTACT=" + sessionManager.getNumber() +
                        "&GENDER=" + sessionManager.getGender() +
                        "&IMAGE=" + sessionManager.getImage() +
                        "&PASSWORD=" + sessionManager.getPassword() +
                        "&BLOODGROUP=" + sessionManager.getBG() +
                        "&LATITUDE=" + sessionManager.getLatitude() +
                        "&LONGITUDE=" + sessionManager.getLongi();

                JSONParser jsonParser = new JSONParser();
                jsonParser.getResponse(update_url, HomeScreen.this,
                        new VolleyCallback() {
                            @Override
                            public void onSuccessResponse(JSONObject response) throws JSONException {
                                //JSONObject response=new JSONObject(result);
                                //Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_SHORT).show();
                                if (response.getString("STATUS").equals("SUCCESS"))
                                    Toast.makeText(getApplicationContext(), "Information has been successfully updated", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), response.getString("MESSAGE"), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Information was not updated", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
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
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profile.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(HomeScreen.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(HomeScreen.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
