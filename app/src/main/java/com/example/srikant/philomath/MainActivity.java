package com.example.srikant.philomath;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_main);
        final EditText emailID = (EditText) findViewById(R.id.emailLogin);
        final EditText password = (EditText) findViewById(R.id.passwordLogin);
        Button login = (Button) findViewById(R.id.loginButton);

        //On Click Login Button Actions
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginID = emailID.getText().toString();
                String loginPassword = password.getText().toString();
                // Connecting to web service
                String IP = getResources().getString(R.string.IpAddress)+"/webapi/login";
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

                            status="Login Successful!";
                            Toast.makeText(MainActivity.this, status, Toast.LENGTH_LONG).show();
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
                }
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


}
