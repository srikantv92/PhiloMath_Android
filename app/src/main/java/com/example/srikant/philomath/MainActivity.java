package com.example.srikant.philomath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_main);
        final EditText emailID = (EditText) findViewById(R.id.emailLogin);
        final EditText password = (EditText) findViewById(R.id.passwordLogin);
        Button login = (Button) findViewById(R.id.loginButton);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String MailID = sharedpreferences.getString("Email", "Not Found");
        if(MailID.contains("@")){
            Intent in= new Intent(MainActivity.this,Homepage.class);
            startActivity(in);
        }

        //On Click Login Button Actions
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                String loginID = emailID.getText().toString();
                String loginPassword = password.getText().toString();
                // Connecting to web service
                String IP = getResources().getString(R.string.IpAddress)+"/webapi/login";
                String[] a={IP,loginID, loginPassword};
                new LoginTask(MainActivity.this).doInBackground(a);
                /*URL url = null;
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

                    JSONObject loginJSON =  new JSONObject();
                    loginJSON.put("email",loginID);
                    loginJSON.put("password",loginPassword);

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
                            Intent success = new Intent(MainActivity.this, Homepage.class);
                            startActivity(success);

                        }
                        else{
                            //login failed
                            status="Login failed! Please check your Email and Password";
                            Toast.makeText(MainActivity.this, status, Toast.LENGTH_LONG).show();
                            Log.d("status", status);
                        }
                    } else {
                        //unsuccessful connection
                        status="Problem in connecting to server";
                        Toast.makeText(MainActivity.this, status, Toast.LENGTH_LONG).show();
                        Log.d("status",status);
                    }

                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });




        TextView register = (TextView) findViewById(R.id.registerNow);

        //on click register now
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(MainActivity.this,Register.class);
                startActivity(registerPage);

            }
        });


        TextView forgot = (TextView) findViewById(R.id.forgotPasswordButton);

        //on click forgot password
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(forgot);
            }
        });

    }
int num = 0;
    public void onBackPressed() {


           /* SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String EmailID = sharedpreferences.getString("Email", "Not Found");
            if (EmailID.equals("Not Found")) {
                Toast.makeText(MainActivity.this, "Session is expired. Please Login!", Toast.LENGTH_LONG).show();
            }*/
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        startActivity(intent);
    }



    /**
     * Created by Srikant on 3/29/2016.
     */


}
