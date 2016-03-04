package com.example.srikant.philomath;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        final EditText pwd = (EditText) findViewById(R.id.pwd);
        final EditText repwd = (EditText) findViewById(R.id.repwd);
        Button submitPwd = (Button) findViewById(R.id.submitPassword);
        submitPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=pwd.getText().toString();
                String repass = repwd.getText().toString();
                if(pass.equals(repass)){
                    Log.d("password","Passwords match");
                    String emailID = getIntent().getExtras().getString("email");
                    String IP = getResources().getString(R.string.IpAddress)+"/webapi/resetpassword";
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
                        JSONObject reset =  new JSONObject();
                        reset.put("email",getIntent().getExtras().getString("email"));
                        reset.put("password",pass);


                        writer.write(reset.toString());
                        writer.close();
                        out.close();
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));
                        String response= rd.readLine();
                        String status;
                        if (conn.getResponseCode() == 200) {
                            if (response.contains("success")) {
                                //successful
                                status="Password reset Successful";
                                Toast.makeText(ResetPassword.this, status, Toast.LENGTH_LONG).show();
                                Log.d("status",status);


                            }
                            else{
                                //failed
                                status="Password reset Failed";
                                Toast.makeText(ResetPassword.this, status, Toast.LENGTH_LONG).show();
                                Log.d("status",status);

                            }
                        } else {
                            //unsuccessful connection
                            status="Problem in connecting to server";
                            Toast.makeText(ResetPassword.this, status, Toast.LENGTH_LONG).show();
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
                else {
                    Log.d("password","Passwords don't match!");
                    Toast.makeText(ResetPassword.this,"Passwords don't match !!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
