package com.mrc.gaurav.asdf;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText editTextUserName; private EditText editTextPassword;
    private String username, password;
    private JSONArray result_json_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login_button);
        editTextUserName = (EditText) findViewById(R.id.login_username);
        editTextPassword = (EditText) findViewById(R.id.login_password);

        SharedPreferences sp = getSharedPreferences("your_prefs", this.MODE_PRIVATE);
        if(sp.contains("username"))
        {
            finish();
            startActivity(new Intent(LoginActivity.this,Navigator.class));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();
                Log.d("loginActivity","login onclick");


                if(username.length() != 0){
                    Log.d("loginActivity","initiate authentication");
                    authenticate();
                }else{
                    Log.d("loginActivity","invalid input"+username+"pass:"+password);
                    Toast.makeText(LoginActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    void authenticate(){

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Processing...");
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                String result = null;

                URL url = null;
                try {
                    url = new URL("http://gauravc4.16mb.com/authenticate.php");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try{
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("username", username)
                            .appendQueryParameter("password", password);
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
                        result="unsucessful";
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace(); result="Error Connecting Server"+e;
                } catch (IOException e) {
                    e.printStackTrace();
                    result = "Error Connecting Server"+e;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                loadingDialog.dismiss();
                if(!result.equalsIgnoreCase("unsuccessful")){
                    try{

                        SharedPreferences sp = getApplicationContext().getSharedPreferences("your_prefs", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        result_json_array = new JSONArray(result);
                        JSONObject result_json_obj = result_json_array.getJSONObject(0);
                        editor.putString("username", result_json_obj.getString("username"));
                        Log.d("loginActivity",result_json_obj.getString("username"));
                        editor.putString("password", result_json_obj.getString("password"));
                        Log.d("loginActivity",result_json_obj.getString("password"));
                        editor.putString("type",result_json_obj.getString("type"));
                        editor.apply();

                    }catch(JSONException e)
                    {
                        Log.d("loginActivity",e.toString());
                    }

                    Intent navigator = new Intent(LoginActivity.this, Navigator.class);
                    startActivity(navigator);
                    finish();

                }else {
                    Log.d("loginActivity",result);
                    Toast.makeText(LoginActivity.this, result+ " fail", Toast.LENGTH_SHORT).show();
                }
            }
        }

        Log.d("loginActivity","authenticate");
        LoginAsync la = new LoginAsync();
        la.execute(username, password);

    }

}
