package com.example.srikant.philomath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Advertise extends AppCompatActivity {
	private static final String IP = "https://intense-thicket-93384.herokuapp.com/webapi/editProfile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advertise);

		final ListView listCoursesTaught = (ListView) findViewById(R.id.listCoursesTaughtAdv);
		final TextView notTeaching = (TextView) findViewById(R.id.notTeachingAdv);

		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		final String EmailID = sharedpreferences.getString("Email", "Not Found");

		try {
			URL url = new URL(IP);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-Type", "text/plain");

			OutputStream out = conn.getOutputStream();
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write(EmailID);
			writer.close();
			Log.d("request sent", "request sent " + EmailID);

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder responseStrBuilder = new StringBuilder();

			String inputStr;
			while ((inputStr = rd.readLine()) != null)
				responseStrBuilder.append(inputStr);
			JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

			JSONArray teacher = jsonObject.getJSONArray("courses");
			List<String> coursesTakenAsStudent = new ArrayList<>();
			final List<String> courses = new ArrayList<>();

			if (teacher != null) {
				for (int i = 0; i < teacher.length(); i++) {
					courses.add(teacher.get(i).toString());
				}
			}

			Log.d("connection response", String.valueOf(conn.getResponseCode()));

			if (conn.getResponseCode() == 200) {

				if (courses.size() == 0) {
					notTeaching.setVisibility(View.VISIBLE);
				}
				ArrayAdapter adapter2 = new ArrayAdapter<String>(Advertise.this, R.layout.list_courses_taught, courses);
				listCoursesTaught.setAdapter(adapter2);

				ViewGroup.LayoutParams listViewParams2 = (ViewGroup.LayoutParams) listCoursesTaught.getLayoutParams();
				listCoursesTaught.measure(0, 0);

				listViewParams2.height = (listCoursesTaught.getMeasuredHeight() * (courses.size()));

				listCoursesTaught.requestLayout();
				listCoursesTaught.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent payment = new Intent(Advertise.this, Payment.class);
						payment.putExtra("course", courses.get(position));
						payment.putExtra("email", EmailID);
						startActivity(payment);
					}
				});
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}