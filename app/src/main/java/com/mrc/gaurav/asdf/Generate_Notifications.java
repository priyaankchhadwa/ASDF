package com.mrc.gaurav.asdf;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mrc.gaurav.asdf.app.AppController;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Generate_Notifications extends Fragment {
    private String username;
    private String type;
    private String URL_FEED = "http://gauravc4.16mb.com/generate_notifications.php";
    private String message;
    private Button generate;
    private EditText getMessage;


    public Generate_Notifications() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        SharedPreferences sp = getActivity().getSharedPreferences("your_prefs", getActivity().MODE_PRIVATE);
        if (!sp.contains("username")) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));

        }
            View v = inflater.inflate(R.layout.fragment_generate__notifications, container, false);
            generate = (Button) v.findViewById(R.id.generate);
            getMessage = (EditText) v.findViewById(R.id.message);

            generate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        checkText(v);
                }
            });
            return v;
    }

    void checkText(View v)
    {
        message = getMessage.getText().toString();
        if(message.equalsIgnoreCase(""))
            Toast.makeText(getActivity(),"Please enter a valid message",Toast.LENGTH_LONG).show();
        else
            feed(v);
    }


    void feed(View v) {

        final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading Data", "Please wait...", false, false);

        StringRequest strRequest = new StringRequest(Request.Method.POST, URL_FEED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("message", message);
                return params;
            }
        };

        loading.dismiss();
        AppController.getInstance().addToRequestQueue(strRequest);

    }

}