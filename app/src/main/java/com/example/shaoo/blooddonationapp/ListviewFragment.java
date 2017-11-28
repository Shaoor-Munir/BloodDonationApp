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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListviewFragment newInstance(String param1, String param2) {
        ListviewFragment fragment = new ListviewFragment();
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
        View view1 = inflater.inflate(R.layout.fragment_listview, container, false);
        ListView listView = (ListView) view1.findViewById(R.id.listView1);
        ListView listView2 = (ListView) view1.findViewById(R.id.listView2);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), SearchFragment.userList, R.layout.result_listview, new String[]{"name", "city", "contact", "image"}, new int[]{R.id.name, R.id.address, R.id.contact, R.id.imageView});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                Map<String, String> map = (Map) parent.getItemAtPosition(position);
                String name = SearchFragment.userList.get(position).get("name").toString();
                String bgroup = SearchFragment.userList.get(position).get("bloodGroup").toString();
                String address = SearchFragment.userList.get(position).get("gender").toString().equals("1") ? "Male" : "Female";
                String contct = SearchFragment.userList.get(position).get("contact").toString();
                String city = SearchFragment.userList.get(position).get("city").toString();
                String title = "Blood Doner";
                String addr = "Gender : ";
                //image = (Bitmap) SearchFragment.userList.get(position).get("image");

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.donor_detail, null);
                TextView nameTXT = (TextView) view.findViewById(R.id.nameTXT);
                TextView bgroupTXT = (TextView) view.findViewById(R.id.bgroupTXT);
                TextView addrs = (TextView) view.findViewById(R.id.Addr);
                TextView addressTXT = (TextView) view.findViewById(R.id.addressTXT);
                TextView contactTXT = (TextView) view.findViewById(R.id.contactTXT);
                TextView cityTXT = (TextView) view.findViewById(R.id.cityTXT);
                //ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                nameTXT.setText(name);
                bgroupTXT.setText(bgroup);
                addressTXT.setText(address);
                contactTXT.setText(contct);
                cityTXT.setText(city);
                addrs.setText(addr);
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
            }
        });
        SimpleAdapter simpleAdapter1 = new SimpleAdapter(getContext(), SearchFragment.BBList, R.layout.result_listview, new String[]{"BankName", "bankCity", "bankContact"}, new int[]{R.id.name, R.id.address, R.id.contact});
        listView2.setAdapter(simpleAdapter1);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                Map<String, String> map = (Map) parent.getItemAtPosition(position);
                String name = SearchFragment.BBList.get(position).get("BankName").toString();
                String bgroup = SearchFragment.BBList.get(position).get("bloodGroup").toString();
                String address = SearchFragment.BBList.get(position).get("bankAddress").toString();
                String contct = SearchFragment.BBList.get(position).get("bankContact").toString();
                String city = SearchFragment.BBList.get(position).get("bankCity").toString();
                String title = "Blood Bank";
                String addr = "Address : ";
                //image = (Bitmap) SearchFragment.userList.get(position).get("image");

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.donor_detail, null);
                TextView nameTXT = (TextView) view.findViewById(R.id.nameTXT);
                TextView bgroupTXT = (TextView) view.findViewById(R.id.bgroupTXT);
                TextView Addr = (TextView) view.findViewById(R.id.Addr);
                TextView addressTXT = (TextView) view.findViewById(R.id.addressTXT);
                TextView contactTXT = (TextView) view.findViewById(R.id.contactTXT);
                TextView cityTXT = (TextView) view.findViewById(R.id.cityTXT);
                //ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                nameTXT.setText(name);
                bgroupTXT.setText(bgroup);
                addressTXT.setText(address);
                contactTXT.setText(contct);
                cityTXT.setText(city);
                Addr.setText(addr);
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
            }
        });
        view1.setFocusableInTouchMode(true);
        view1.requestFocus();
        view1.setOnKeyListener(new View.OnKeyListener() {
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
        return view1;
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
