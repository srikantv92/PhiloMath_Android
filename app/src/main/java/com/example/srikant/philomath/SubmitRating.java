package com.example.srikant.philomath;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubmitRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_rating);

        final TextView prof= (TextView) findViewById(R.id.prof_name);
        prof.setText(getIntent().getExtras().getString("professor"));

        final TextView course= (TextView) findViewById(R.id.course_name);
        course.setText(getIntent().getExtras().getString("course"));

        final RatingBar rating_clarity= (RatingBar) findViewById(R.id.rating_clarity);
        rating_clarity.setRating(Float.parseFloat(getIntent().getExtras().getString("clarity")));

        final RatingBar rating_helpfulness= (RatingBar) findViewById(R.id.rating_helpful);
        rating_helpfulness.setRating(Float.parseFloat(getIntent().getExtras().getString("helpfulness")));

        final RatingBar rating_easiness= (RatingBar) findViewById(R.id.rating_easiness);
        rating_helpfulness.setRating(Float.parseFloat(getIntent().getExtras().getString("easiness")));

        final RatingBar rating_overall= (RatingBar) findViewById(R.id.rating_overall);
        rating_helpfulness.setRating(Float.parseFloat(getIntent().getExtras().getString("overall")));

        final Button submit = (Button) findViewById(R.id.submitRating);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating=Float.parseFloat(getIntent().getExtras().getString("overall"));
                int num = Integer.parseInt(getIntent().getExtras().getString("numberOfVotes"));


                String profEmail=getIntent().getExtras().getString("profEmail");
                String course=getIntent().getExtras().getString("course");
                rating= rating*(num)+rating_overall.getRating();
                num++;
                rating=rating/num;
                String IP = getResources().getString(R.string.IpAddress)+"/webapi/updateRating";
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
                            "text/plain");
                    OutputStream out = conn.getOutputStream();
                    Writer writer = new OutputStreamWriter(out, "UTF-8");
                    Log.d("req",profEmail + "," + course + "," + rating + "," + num);
                    writer.write(profEmail + "," + course + "," + rating + "," + num);
                    writer.close();
                    out.close();
                    if(conn.getResponseCode()==200){
                        Toast.makeText(SubmitRating.this,"Your Rating Submitted!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("resp", String.valueOf(conn.getResponseCode()) );
                                Toast.makeText(SubmitRating.this, "Error in submitting rating!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception ex){

                }
            }
        });

    }


}
