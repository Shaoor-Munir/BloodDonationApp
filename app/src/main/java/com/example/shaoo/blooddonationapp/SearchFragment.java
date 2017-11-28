package com.example.shaoo.blooddonationapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static List<Map<String, Object>> userList = new LinkedList<>();
    public static List<Map<String, Object>> BBList = new LinkedList<>();
    String bloodGroup = "";
    LocationManager mLocationManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.logSign);
        if (mParam2.equals("Launcher")) {
            layout.setVisibility(View.VISIBLE);
        } else if (mParam2.equals("Home")) {
            layout.setVisibility(View.GONE);
        }
        userList.clear();
        BBList.clear();

        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final SwitchCompat sw = (SwitchCompat) view.findViewById(R.id.map_view_switch);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw.isChecked()) {
                    sw.setText("List View");
                } else {
                    sw.setText("Map View");
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);

        final Button button = (Button) view.findViewById(R.id.search_button_location);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), LauncherActivity.latitude + " : " + LauncherActivity.longitude, Toast.LENGTH_SHORT).show();
                JSONParser jsonParser = new JSONParser();
                if (!bloodGroup.equals("Blood Type")) {
                    jsonParser.getResponse(CellnoAndMailVerify.getUsersNearby(20, 10, bloodGroup), getContext(),
                            new VolleyCallback() {
                                @Override
                                public void onSuccessResponse(JSONObject result) {
                                    try {
                                        Log.d("Response : ", result.toString());
                                        if (result.getString("STATUS").equals("SUCCESS")) {
                                            JSONArray usersArray = result.getJSONArray("USERS");
                                            JSONArray BBArray = result.getJSONArray("BANKS");
                                            for (int i = 0; i < usersArray.length(); i++) {
                                                JSONObject user = usersArray.getJSONObject(i);
                                                Map<String, Object> map = new HashMap<>();
                                                map.put("id", user.getInt("id"));
                                                map.put("email", user.getString("email"));
                                                map.put("password", user.getString("password"));
                                                map.put("name", user.getString("name"));
                                                map.put("age", user.getInt("age"));
                                                map.put("bloodGroup", user.getString("bloodGroup"));
                                                map.put("gender", (user.getInt("gender") == 1) ? "Male" : "female");
                                                map.put("contact", user.getString("contact"));
                                                map.put("city", user.getString("city"));
                                                map.put("longitude", user.getDouble("longi"));
                                                map.put("latitude", user.getDouble("lati"));
                                                map.put("available", user.getInt("available") == 1 ? "yes" : "NO");
                                                String encodedImage = user.getString("image");
                                                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                                map.put("image", decodedByte);
                                                userList.add(map);
                                            }

                                            for (int i = 0; i < BBArray.length(); i++) {
                                                JSONObject bank = BBArray.getJSONObject(i);
                                                Map<String, Object> map = new HashMap<>();
                                                map.put("id", bank.getInt("sampleID"));
                                                map.put("bankID", bank.getInt("bankID"));
                                                map.put("BankName", bank.getString("bankName"));
                                                map.put("bankAddress", bank.getString("bankAddress"));
                                                map.put("bankCity", bank.getString("bankCity"));
                                                map.put("bloodGroup", bank.getString("bloodGroup"));
                                                map.put("bankContact", bank.getString("bankContact"));
                                                map.put("isHospital", bank.getInt("isHospital") == 1 ? "Yes" : "No");
                                                map.put("longitude", bank.getDouble("longitude"));
                                                map.put("latitude", bank.getDouble("latitude"));
                                                map.put("available", bank.getInt("isAvailable") == 1 ? "yes" : "NO");
                                                BBList.add(map);
                                            }

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    if (sw.isChecked()) {
                        Fragment selectedFragment = ListviewFragment.newInstance(null, null);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, selectedFragment);
                        transaction.commit();
                    } else {
                        Fragment selectedFragment = MapFragment.newInstance(null, null);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, selectedFragment);
                        transaction.commit();
                    }
                } else
                    Toast.makeText(getContext(), "Choose your blood group first", Toast.LENGTH_SHORT).show();
            }
        });
        Spinner spinner = (Spinner) view.findViewById(R.id.appCompatSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = getResources().getStringArray(R.array.bloodgrp_dropdown_arrays)[position];
                char c = bloodGroup.charAt(bloodGroup.length() - 1);
                if (c == '+')
                    bloodGroup = bloodGroup.replace("+", "P");
                else
                    bloodGroup = bloodGroup.replace("-", "N");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setHint("Search for a place");
        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button.setTag("curr");
                        button.setText("Search by current location");
                    }
                });

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                LauncherActivity.longitude = place.getLatLng().longitude;
                LauncherActivity.latitude = place.getLatLng().latitude;
                Toast.makeText(getContext(), "Place: " + place.getName(), Toast.LENGTH_SHORT).show();
                button.setText("Search by place name");
                button.setTag("place");
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(getContext(), "An error occurred: " + status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
