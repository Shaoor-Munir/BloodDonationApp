package com.example.shaoo.blooddonationapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * { interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap googleMap;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment selectedFragment = SearchFragment.newInstance(null, "Launcher");
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, selectedFragment);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });

        mMapView = (MapView) view.findViewById(R.id.blood_map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                int count = 0;
                for (Map<String, Object> map : SearchFragment.userList) {
                    double longitude = (double) map.get("longitude");
                    double latitude = (double) map.get("latitude");
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.blood)));
                    marker.setTag("UR " + count);
                    count++;
                }
                count = 0;
                for (Map<String, Object> map : SearchFragment.BBList) {
                    double longitude = (double) map.get("longitude");
                    double latitude = (double) map.get("latitude");
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));
                    marker.setTag("BB " + count);
                    count++;
                }
                LatLng coordinate = new LatLng(LauncherActivity.latitude, LauncherActivity.longitude);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 10);
                mMap.animateCamera(yourLocation);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String[] str = marker.getTag().toString().split(" ");
                        int position = Integer.parseInt(str[1]);
                        String name = null, bgroup = null, address = null, contct = null, city = null, title = null, addr = null;
                        //Bitmap image;
                        if (str[0].equals("UR")) {
                            name = SearchFragment.userList.get(position).get("name").toString();
                            bgroup = SearchFragment.userList.get(position).get("bloodGroup").toString();
                            address = SearchFragment.userList.get(position).get("gender").toString().equals("1") ? "Male" : "Female";
                            contct = SearchFragment.userList.get(position).get("contact").toString();
                            city = SearchFragment.userList.get(position).get("city").toString();
                            addr = "Gender : ";
                            title = "Blood Doner";
                            //image = (Bitmap) SearchFragment.userList.get(position).get("image");
                        } else if (str[0].equals("BB")) {
                            name = SearchFragment.BBList.get(position).get("BankName").toString();
                            bgroup = SearchFragment.BBList.get(position).get("bloodGroup").toString();
                            address = SearchFragment.BBList.get(position).get("bankAddress").toString();
                            contct = SearchFragment.BBList.get(position).get("bankContact").toString();
                            city = SearchFragment.BBList.get(position).get("bankCity").toString();
                            title = "BloodBank";
                            addr = "Address : ";
                            //image = (Bitmap) SearchFragment.userList.get(position).get("image");
                        }
                        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = layoutInflater.inflate(R.layout.donor_detail, null);
                        TextView nameTXT = (TextView) view.findViewById(R.id.nameTXT);
                        TextView bgroupTXT = (TextView) view.findViewById(R.id.bgroupTXT);
                        TextView addressTXT = (TextView) view.findViewById(R.id.addressTXT);
                        TextView AddrTXT = (TextView) view.findViewById(R.id.Addr);
                        TextView contactTXT = (TextView) view.findViewById(R.id.contactTXT);
                        TextView cityTXT = (TextView) view.findViewById(R.id.cityTXT);
                        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                        nameTXT.setText(name);
                        bgroupTXT.setText(bgroup);
                        addressTXT.setText(address);
                        AddrTXT.setText(addr);
                        contactTXT.setText(contct);
                        cityTXT.setText(city);
                        //imageView.setImageBitmap(image);
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setIcon(R.drawable.blood).setTitle(title + " Detail")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setView(view).create();
                        alertDialog.show();
                        //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });


            }
        });
        return view;
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

}
