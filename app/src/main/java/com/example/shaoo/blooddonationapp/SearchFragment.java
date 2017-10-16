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
    double longitude, latitude;
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
//         = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sw.isChecked())
                {
                    sw.setText("List View");
                }
                else
                {
                    sw.setText("Map View");
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);

        final Button button = (Button) view.findViewById(R.id.search_button_location);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checkLocation();
                Toast.makeText(getContext(), latitude + " : " + longitude, Toast.LENGTH_SHORT).show();
                JSONParser jsonParser = new JSONParser();
                if (!bloodGroup.equals("Blood Type")) {
//                    jsonParser.getResponse(CellnoAndMailVerify.getUsersNearby(10, 20, "AN"), getContext(),
//                            new VolleyCallback() {
//                                @Override
//                                public void onSuccessResponse(String result) {
//                                    try {
//                                        //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_SHORT).show();
//                                        JSONObject response = new JSONObject(result);
//                                        String result1 = result.substring(response.toString().length(), result.length());
//                                        JSONObject response1 = new JSONObject(result1);
//                                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(getContext(), response1.toString(), Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
////                                        String Bloodbank=response.getJSONObject(0).getString("STATUSBB");
////                                        String User=response.getJSONObject(1).getString("STATUS");
////                                        JSONArray BBarray=response.getJSONObject(0).getJSONArray("DATABB");
////                                        JSONArray Userarray=response.getJSONObject(1).getJSONArray("DATA");
//
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
                    int gen = 1;
                    int avail = 1;
                    Integer id = 1;
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id + "");
                    map.put("email", "Aqeel");
                    map.put("password", "myPass");
                    map.put("name", "Aqeel");
                    map.put("age", 22 + "");
                    map.put("bloodGroup", "B");
                    map.put("gender", (gen == 1) ? "Male" : "female");
                    map.put("contact", " 9232322323");
                    map.put("city", "Lahore");
                    map.put("longitude", 12.2545 + "");
                    map.put("latitude", 21.3542 + "");
                    map.put("available", avail == 1 ? "yes" : "NO");
                    String encodedImage = " data:image/jpeg;base64,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";
                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    map.put("image", decodedByte);
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("id", id + 1 + "");
                    map1.put("email", "Ehsan");
                    map1.put("password", "myPass");
                    map1.put("name", "Ehsan");
                    map1.put("age", 22 + "");
                    map1.put("bloodGroup", "B");
                    map1.put("gender", (gen == 1) ? "Male" : "female");
                    map1.put("contact", " 9232322323");
                    map1.put("city", "Lahore");
                    map1.put("longitude", 12.20 + "");
                    map1.put("latitude", 21.30 + "");
                    map1.put("available", avail == 1 ? "yes" : "NO");
                    map1.put("image", decodedByte);
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("id", id + "");
                    map2.put("bankID", 1 + "");
                    map2.put("BankName", "bbl");
                    map2.put("bankAddress", "addr");
                    map2.put("bankCity", "cia");
                    map2.put("bloodGroup", "B");
                    map2.put("bankContact", "304405405");
                    map2.put("isHospital", avail == 1 ? "Yes" : "No");
                    map2.put("longitude", 12.2545 + "");
                    map2.put("latitude", 21.3542 + "");
                    map2.put("available", avail == 1 ? "yes" : "NO");
                    userList.add(map);
                    userList.add(map1);
                    BBList.add(map2);
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
                longitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;
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
//    LocationListener mLocationListener;
//    Location location;
//    public void checkLocation() {
//
//        String serviceString = Context.LOCATION_SERVICE;
//        mLocationManager = (LocationManager) getContext().getSystemService(serviceString);
//
//
//        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//
//        mLocationListener = new android.location.LocationListener() {
//            public void onLocationChanged(Location locationListener) {
//
//                if (isGPSEnabled(getContext())) {
//                    if (locationListener != null) {
//                        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//
//                        if (mLocationManager != null) {
//                            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                            if (location != null) {
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                            }
//                        }
//                    }
//                } else {
//                    Toast.makeText(getContext(),"Turn On GPS First!",Toast.LENGTH_SHORT).show();
//                    if (isInternetConnected(getContext())) {
//                        if (mLocationManager != null) {
//                            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                            if (location != null) {
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                            }
//                        }
//                    }
//                }
//            }
//
//            public void onProviderDisabled(String provider) {
//
//            }
//
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//        };
//
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
//    }
//    public static boolean isInternetConnected(Context ctx) {
//        ConnectivityManager connectivityMgr = (ConnectivityManager) ctx
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifi = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo mobile = connectivityMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        // Check if wifi or mobile network is available or not. If any of them is
//        // available or connected then it will return true, otherwise false;
//        if (wifi != null) {
//            if (wifi.isConnected()) {
//                return true;
//            }
//        }
//        if (mobile != null) {
//            if (mobile.isConnected()) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public boolean isGPSEnabled(Context mContext) {
//        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//    }

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
