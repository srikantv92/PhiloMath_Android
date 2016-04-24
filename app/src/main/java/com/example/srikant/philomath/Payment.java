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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        final String course= getIntent().getExtras().getString("course");
        final String Email=  getIntent().getExtras().getString("email");
        final TextView cour = (TextView) findViewById(R.id.AdvtCourse);
        final EditText card = (EditText) findViewById(R.id.AdvtCard);
        final EditText date = (EditText) findViewById(R.id.AdvtDate);
        final EditText cvv =(EditText) findViewById(R.id.AdvtCVV);
        final EditText name =(EditText) findViewById(R.id.AdvtName);
        cour.setText(course.toUpperCase());
        final Button submitPayment = (Button) findViewById(R.id.submitPayment);
        submitPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("payment", "submit clicked");
                if (name.getText().toString().isEmpty() || card.getText().toString().isEmpty() || date.getText().toString().isEmpty() || cvv.getText().toString().isEmpty()) {
                    Toast.makeText(Payment.this,"Please Enter all the Fields!!", Toast.LENGTH_SHORT).show();
                } else {
                    String IP = "https://intense-thicket-93384.herokuapp.com/webapi/promote";
                    try {
                        URL url = new URL(IP);
                        HttpURLConnection conn =
                                (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setUseCaches(false);
                        conn.setAllowUserInteraction(false);
                        conn.setRequestProperty("Content-Type",
                                "text/plain");

                        OutputStream out = conn.getOutputStream();
                        Writer writer = new OutputStreamWriter(out, "UTF-8");
                        writer.write(Email + ";" + course);
                        Log.d("payment", Email + ";" + course);
                        writer.close();
                        Log.d("payment", String.valueOf(conn.getResponseCode()));
                        Intent home = new Intent(Payment.this,Homepage.class);
                        Toast.makeText(Payment.this,"You Successfully advertised your course", Toast.LENGTH_LONG).show();
                        startActivity(home);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            });

    }

}
