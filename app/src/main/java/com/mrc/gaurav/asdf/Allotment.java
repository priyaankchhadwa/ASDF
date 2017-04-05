package com.mrc.gaurav.asdf;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Allotment extends Fragment {


    private String username;

    public Allotment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        SharedPreferences sp = getActivity().getSharedPreferences("your_prefs",getActivity().MODE_PRIVATE);
        if(!sp.contains("username"))
        {
            getActivity().finish();
            startActivity(new Intent(getActivity(),LoginActivity.class));

        }
        else{
            username = sp.getString("username","");
        }
        return inflater.inflate(R.layout.fragment_allotment, container, false);
    }

}
