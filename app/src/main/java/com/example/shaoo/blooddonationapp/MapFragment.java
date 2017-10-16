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
                    double longitude = Double.parseDouble((String) map.get("longitude"));
                    double latitude = Double.parseDouble((String) map.get("latitude"));
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.blood)));
                    marker.setTag(count);
                    count++;
                }
                LatLng coordinate = new LatLng(21.3, 12.2);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 8);
                mMap.animateCamera(yourLocation);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        int position = (int) (marker.getTag());
                        String name = (String) SearchFragment.userList.get(position).get("name");
                        String age = (String) SearchFragment.userList.get(position).get("age");
                        String gender = (String) SearchFragment.userList.get(position).get("gender");
                        String bgroup = (String) SearchFragment.userList.get(position).get("bloodGroup");
                        String address = (String) SearchFragment.userList.get(position).get("city");
                        String contct = (String) SearchFragment.userList.get(position).get("contact");
                        String email = (String) SearchFragment.userList.get(position).get("email");
                        Bitmap image = (Bitmap) SearchFragment.userList.get(position).get("image");
                        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = layoutInflater.inflate(R.layout.donor_detail, null);
                        TextView nameTXT = (TextView) view.findViewById(R.id.nameTXT);
                        TextView ageTXT = (TextView) view.findViewById(R.id.ageTXT);
                        TextView genderTXT = (TextView) view.findViewById(R.id.genderTXT);
                        TextView bgroupTXT = (TextView) view.findViewById(R.id.bgroupTXT);
                        TextView addressTXT = (TextView) view.findViewById(R.id.addressTXT);
                        TextView contactTXT = (TextView) view.findViewById(R.id.contactTXT);
                        TextView emailTXT = (TextView) view.findViewById(R.id.emailTXT);
                        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                        nameTXT.setText(name);
                        ageTXT.setText(age);
                        genderTXT.setText(gender);
                        bgroupTXT.setText(bgroup);
                        addressTXT.setText(address);
                        contactTXT.setText(contct);
                        emailTXT.setText(email);
                        imageView.setImageBitmap(image);
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setIcon(R.drawable.blood).setTitle("Blood Doner Detail")
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


//                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                    @Override
//                    public void onInfoWindowClick(Marker marker) {
//                        //markerClicked(marker);
//                        Toast.makeText(getContext(), "The marker is clicked.", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                });

                // For showing a move to my location button
                // googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                //addMarkers();
                // For zooming automatically to the location of the marker
                //CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
