package com.mrc.gaurav.asdf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mrc.gaurav.asdf.adapter.FeedListAdapter;
import com.mrc.gaurav.asdf.app.AppController;
import com.mrc.gaurav.asdf.data.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.JsonArrayRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://gauravc4.16mb.com/notifications.php";
    private String username;
    private JSONArray result_json_array;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences("your_prefs",getActivity().MODE_PRIVATE);
        if(!sp.contains("username")){
            getActivity().finish();
            startActivity(new Intent(getActivity(),LoginActivity.class));

        }
        else{
            username = sp.getString("username","no value found");
            Log.d("profile",username);
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        feed(v);
        return v;
    }

    void feed(View v) {

        listView = (ListView) v.findViewById(R.id.notification_list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        final ProgressDialog loading = ProgressDialog.show(getContext(), "Loading Data", "Please wait...", false, false);

        StringRequest strRequest = new StringRequest(Request.Method.POST, URL_FEED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            result_json_array = new JSONArray(response);

                            for (int i = 0; i < result_json_array.length(); i++) {

                                FeedItem item = new FeedItem();
                                JSONObject feedObj = null;

                                    feedObj = result_json_array.getJSONObject(i);
                                    Log.d("profile",feedObj.toString());
                                    if(i == 0){
                                        item.setNotificationDate(feedObj.getString("prim"));
                                        item.setNotificationText(feedObj.getString("username"));
                                    }
                                    else {
                                        item.setNotificationDate(feedObj.getString("date"));
                                        item.setNotificationText(feedObj.getString("message"));
                                    }

                                    loading.dismiss();

                                feedItems.add(item);
                            }

                            listAdapter.notifyDataSetChanged();

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                return params;
            }
        };
        loading.dismiss();
        AppController.getInstance().addToRequestQueue(strRequest);

    }

}
