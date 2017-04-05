package com.mrc.gaurav.asdf;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mrc.gaurav.asdf.adapter.FeedListAdapter_pref;
import com.mrc.gaurav.asdf.app.AppController;
import com.mrc.gaurav.asdf.data.FeedItem_pref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Preferences extends Fragment {

    private ListView listView;
    private FeedListAdapter_pref listAdapter;
    private List<FeedItem_pref> feedItems;
    private String URL_FEED = "http://intruding-decay.000webhostapp.com/pref_test.php";
    private String username;
    private ProgressDialog loading;
           // ProgressDialog.show(getContext(),"Loading Data","Please Wait ...");
   // private final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);

    public Preferences() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences("your_prefs",getActivity().MODE_PRIVATE);
        if(!sp.contains("username"))
        {
            getActivity().finish();
            startActivity(new Intent(getActivity(),Navigator.class));
        }
        else{
            username = sp.getString("username","");
        }

        feed(v);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(loading!=null && loading.isShowing())
            loading.dismiss();
    }

    void feed(View v){

        listView = (ListView) v.findViewById(R.id.preferences_list);

        feedItems = new ArrayList<FeedItem_pref>();

        listAdapter = new FeedListAdapter_pref(getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        loading = new ProgressDialog(getContext());
        loading.setTitle("Loading Data");
        loading.setMessage("Please Wait....");
        loading.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_FEED,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("Pref",""+response);
                        if(loading!=null && loading.isShowing())
                            loading.dismiss();
                        parseJsonFeed(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(loading!=null && loading.isShowing())
                            loading.dismiss();
                        error.printStackTrace();
                    }
                });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    private void parseJsonFeed(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {

            FeedItem_pref item = new FeedItem_pref();
            JSONObject feedObj = null;

            try{
                feedObj = response.getJSONObject(i);

                /*if(feedObj.getString("pref_slot1_leave_pref").equals("leave") &&
                        feedObj.getString("pref_slot2_leave_pref").equals("leave")){
                    //leave
                }else{*/
                item.setCount(String.valueOf(i+1));
                item.setPref_id(feedObj.getString("pref_id"));
                item.setPref_date(feedObj.getString("pref_date"));
                item.setPref_slot1_leave_pref(feedObj.getString("pref_slot1_leave_pref"));
                item.setPref_slot2_leave_pref(feedObj.getString("pref_slot2_leave_pref"));
                //}

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            feedItems.add(item);
        }

        listAdapter.notifyDataSetChanged();
    }

}
