package com.example.shaoo.blooddonationapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        this.photoID = photoID;
    }
}

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<CardData> data;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

            data = new ArrayList<>();
            data.add(new CardData("Test Card 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sem turpis, tristique ut eros vel, maximus pulvinar ante. Praesent diam velit, laoreet et ligula a, pellentesque laoreet lorem. Mauris in libero vitae purus posuere convallis. ", R.drawable.cheeta));
            data.add(new CardData("Test Card 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sem turpis, tristique ut eros vel, maximus pulvinar ante. Praesent diam velit, laoreet et ligula a, pellentesque laoreet lorem. Mauris in libero vitae purus posuere convallis. ", R.drawable.cheeta));
            data.add(new CardData("Test Card 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sem turpis, tristique ut eros vel, maximus pulvinar ante. Praesent diam velit, laoreet et ligula a, pellentesque laoreet lorem. Mauris in libero vitae purus posuere convallis. ", R.drawable.cheeta));
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
