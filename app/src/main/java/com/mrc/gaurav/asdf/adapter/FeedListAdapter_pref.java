package com.mrc.gaurav.asdf.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.mrc.gaurav.asdf.LoginActivity;
import com.mrc.gaurav.asdf.Navigator;
import com.mrc.gaurav.asdf.R;
import com.mrc.gaurav.asdf.data.FeedItem_pref;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by steven on 21-03-2017.
 */

public class FeedListAdapter_pref extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem_pref> feedItems;

    public FeedListAdapter_pref(Activity activity, List<FeedItem_pref> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item_pref, null);

        TextView idtext = (TextView) convertView.findViewById(R.id.idtext);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        final Spinner spinner1 = (Spinner) convertView.findViewById(R.id.pref_slot1_option);
        final Spinner spinner2 = (Spinner) convertView.findViewById(R.id.pref_slot2_option);
        final RelativeLayout feed_item_pref = (RelativeLayout) convertView.findViewById(R.id.feed_item_pref);

        final FeedItem_pref item = feedItems.get(position);

        date.setText(item.getPref_date());
        idtext.setText(item.getCount());

        String pref_slot1 = item.getPref_slot1_leave_pref();
        String pref_slot2 = item.getPref_slot2_leave_pref();

        //spinner items
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.pref_options, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        final int spinner1_selection, spinner2_selection;

        //spinner set selections
        if(pref_slot1.equals("leave") || pref_slot1.equals("pref_n")){
            spinner1.setSelection(2);
            feed_item_pref.setBackgroundColor(Color.parseColor("#e9cfec"));
            spinner1_selection = 2;
        }else if(pref_slot1.equals("pref_y")){
            spinner1.setSelection(1);
            feed_item_pref.setBackgroundColor(Color.parseColor("#e9cfec"));
            spinner1_selection = 1;
        }else{
            spinner1.setSelection(0);
            spinner1_selection = 0;
        }

        if(pref_slot2.equals("leave") || pref_slot2.equals("pref_n")){
            spinner2.setSelection(2);
            feed_item_pref.setBackgroundColor(Color.parseColor("#e9cfec"));
            spinner2_selection = 2;
        }else if(pref_slot2.equals("pref_y")){
            spinner2.setSelection(1);
            feed_item_pref.setBackgroundColor(Color.parseColor("#e9cfec"));
            spinner2_selection = 1;
        }else{
            spinner2.setSelection(0); spinner2_selection = 0;
        }

        //spinner onselections
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner1_selection != position){
                    UpdatePref(String.valueOf(position), item.getPref_id(), String.valueOf(1));
                    if(spinner1.getSelectedItemPosition() == 0 && spinner2.getSelectedItemPosition() == 0){
                        feed_item_pref.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner2_selection != position){
                    UpdatePref(String.valueOf(position), item.getPref_id(), String.valueOf(2));
                    if(spinner1.getSelectedItemPosition() == 0 && spinner2.getSelectedItemPosition() == 0){
                        feed_item_pref.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return convertView;
    }

    void UpdatePref(final String option_position, final String pref_id, final String slot){

        class Update_pref extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(activity, "Please wait", "Processing...");
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                String result = null;

                URL url = null;
                try {
                    url = new URL("http://intruding-decay.000webhostapp.com/update_pref.php");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try{
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("option_position", option_position)
                            .appendQueryParameter("pref_id", pref_id)
                            .appendQueryParameter("slot", slot);
                    String query = builder.build().getEncodedQuery();


                    DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(dStream, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    dStream.close();
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        result = sb.toString();
                    }else{
                        result="unsucessfull";
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace(); result="Error Connecting Server";
                } catch (IOException e) {
                    e.printStackTrace();
                    result = "Error Connecting Server";
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String[] separated = result.split(":");
                loadingDialog.dismiss();
                if(separated[0].equalsIgnoreCase("success")){
                    Toast.makeText(activity, "Preference Updated Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("result:",result);
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                }
            }
        }

        Log.d("place","UpdatePref");
        Update_pref up = new Update_pref();
        up.execute(option_position, pref_id, slot);

    }

}
