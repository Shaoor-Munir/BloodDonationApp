package com.example.shaoo.blooddonationapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


class CardData{
    String title;
    String description;
    int photoID;

    CardData(String title, String description, int photoID)
    {
        this.title = title;
        this.description = description;
        this.setPhotoID(photoID);
    }
public int getPhotoID() {
    return photoID;
}
public void setPhotoID(int photoID) {
    this.photoID = photoID;
}}

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    JSONArray arr;

    private List<CardData> data;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    JSONObject obj;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            obj = new JSONObject();


            String url ="http://sundarsharif.com/onbloodtest/servercontroller.php?REQUEST_TYPE=GETQUOTES";
            RequestQueue queue = Volley.newRequestQueue(getContext());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    obj = response;
                    String Res = response.toString();
                    Toast.makeText(getContext(),Res,Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),
                            "Error in volley", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsonObjReq);


           //  Parsing the json response and rendering it on the screen
//            try {
//                if(obj.getString("STATUS") == "FAIL"){
//                    Toast.makeText(getContext(),"No quotes Retrieved",Toast.LENGTH_LONG).show();
//                }
//                else if(obj.getString("STATUS") == "SUCCESS") {
//                    arr = obj.getJSONArray("DATA");
//                    int length = obj.length();
//                    data = new ArrayList<>();
//                    for(int i=0; i<length; i++) {
//                        JSONObject jsonObj = arr.getJSONObject(i);
//                        String image = jsonObj.getString("image");
//                        String heading = jsonObj.getString("heading");
//                        String description = jsonObj.getString("description");
//                        data.add(new CardData(heading,description, Picasso.with(getContext()).load(image)));
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View HView = inflater.inflate(R.layout.fragment_home, container, false);


        RecyclerView rv = (RecyclerView)HView.findViewById(R.id.home_rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(data);
        rv.setAdapter(adapter);

        return HView;
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
