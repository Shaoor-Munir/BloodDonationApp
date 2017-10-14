package com.example.shaoo.blooddonationapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    public static Map<Integer, Map<String, String>> userMap = new HashMap<>();
    public static Map<Integer, Map<String, String>> BBMap = new HashMap<>();
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
                    jsonParser.getResponse(CellnoAndMailVerify.getUsersNearby(10, 20, "AN"), getContext(),
                            new VolleyCallback() {
                                @Override
                                public void onSuccessResponse(String result) {
                                    try {
                                        //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_SHORT).show();
                                        JSONObject response = new JSONObject(result);
                                        String result1 = result.substring(response.toString().length(), result.length());
                                        JSONObject response1 = new JSONObject(result1);
                                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getContext(), response1.toString(), Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(getContext(),response.toString(),Toast.LENGTH_SHORT).show();
//                                        String Bloodbank=response.getJSONObject(0).getString("STATUSBB");
//                                        String User=response.getJSONObject(1).getString("STATUS");
//                                        JSONArray BBarray=response.getJSONObject(0).getJSONArray("DATABB");
//                                        JSONArray Userarray=response.getJSONObject(1).getJSONArray("DATA");


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
//                    int gen=1;
//                    int avail=1;
//                    Integer id=1;
//                    Map<String,String> map=new HashMap<String, String>();
//                    map.put("email","Aqeel");
//                    map.put("password","myPass");
//                    map.put("name","Aqeel");
//                    map.put("age",22+"");
//                    map.put("bloodGroup","B");
//                    map.put("gender",(gen==1)?"Male":"female");
//                    map.put("contact"," 9232322323");
//                    map.put("city","Lahore");
//                    map.put("longitude",12.2545+"");
//                    map.put("latitude",21.3542+"");
//                    map.put("available",avail==1?"yes":"NO");
//                    map.put("image","");
//                    Map<String,String> map1=new HashMap<String, String>();
//                    map1.put("email","Ehsan");
//                    map1.put("password","myPass");
//                    map1.put("name","Ehsan");
//                    map1.put("age",22+"");
//                    map1.put("bloodGroup","B");
//                    map1.put("gender",(gen==1)?"Male":"female");
//                    map1.put("contact"," 9232322323");
//                    map1.put("city","Lahore");
//                    map1.put("longitude",12.20+"");
//                    map1.put("latitude",21.30+"");
//                    map1.put("available",avail==1?"yes":"NO");
//                    map1.put("image","");
//                    Map<String,String> map2=new HashMap<String, String>();
//                    map2.put("bankID",1+"");
//                    map2.put("BankName","bbl");
//                    map2.put("bankAddress","addr");
//                    map2.put("bankCity","cia");
//                    map2.put("bloodGroup","B");
//                    map2.put("bankContact","304405405");
//                    map2.put("isHospital",avail==1?"Yes":"No");
//                    map2.put("longitude",12.2545+"");
//                    map2.put("latitude",21.3542+"");
//                    map2.put("available",avail==1?"yes":"NO");
//                    userMap.put(id,map);
//                    userMap.put(id+1,map1);
//                    BBMap.put(id,map2);
//                    Fragment selectedFragment = MapFragment.newInstance(null, null);
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.content, selectedFragment);
//                    transaction.commit();
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
