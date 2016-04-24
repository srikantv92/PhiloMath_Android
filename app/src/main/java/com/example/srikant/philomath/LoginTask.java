package com.example.srikant.philomath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginTask extends AsyncTask<String, Integer, Long> {
    Context context;

    public LoginTask(Context context){
        this.context=context;

    }

    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected Long doInBackground(String... params) {

        String IP =  params[0];
        String loginID =  params[1];
        String loginPassword=  params[2];
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        URL url = null;
        try {

            url = new URL(IP);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type",
                    "application/json");

            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");

            //json input to web service

            JSONObject loginJSON = new JSONObject();
            loginJSON.put("email", loginID);
            loginJSON.put("password", loginPassword);

            writer.write(loginJSON.toString());
            writer.close();
            out.close();

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            String status;

            if (conn.getResponseCode() == 200) {

                if (response.contains("success")) {
                    //successful Login
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Email",loginID);
                    editor.putString("Password",loginPassword);
                    editor.commit();
                    status="Login Successful!";


                    Log.d("status", status);
                    Intent success = new Intent(context, Homepage.class);

                    context.startActivity(success);

                }
                else{
                    //login failed

                    status="Login failed! Please check your Email and Password";
                    Toast.makeText(context, status, Toast.LENGTH_LONG).show();
                    Log.d("status", status);
                }
            } else {
                //unsuccessful connection
                status="Problem in connecting to server";
                Toast.makeText(context, status, Toast.LENGTH_LONG).show();
                Log.d("status",status);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
