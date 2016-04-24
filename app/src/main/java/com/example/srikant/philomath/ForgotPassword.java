package com.example.srikant.philomath;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText email = (EditText) findViewById(R.id.emailForgotPassword);
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                String emailID = email.getText().toString();

                String IP = getResources().getString(R.string.IpAddress)+"/webapi/forgotpassword";
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
                    JSONObject forgot =  new JSONObject();
                    forgot.put("email",emailID);


                    writer.write(forgot.toString());
                    writer.close();
                    out.close();
                    BufferedReader rd = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    StringBuilder responseStrBuilder = new StringBuilder();

                    String inputStr;
                    while ((inputStr = rd.readLine()) != null)
                        responseStrBuilder.append(inputStr);
                    JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                    String status;
                    if (conn.getResponseCode() == 200) {
                        if (!jsonObject.get("ans").toString().isEmpty()) {
                            //successful
                            Intent seq = new Intent(ForgotPassword.this,SecurityQuestion.class);
                            seq.putExtra("seq",jsonObject.getString("seq"));
                            seq.putExtra("ans",jsonObject.getString("ans"));
                            seq.putExtra("email",emailID);
                            startActivity(seq);


                        }
                        else{
                            //failed
                            status="User is not registered";
                            Toast.makeText(ForgotPassword.this, status, Toast.LENGTH_LONG).show();
                            Log.d("status",status);

                        }
                    } else {
                        //unsuccessful connection
                        status="Problem in connecting to server";
                        Toast.makeText(ForgotPassword.this, status, Toast.LENGTH_LONG).show();
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

    }

}
