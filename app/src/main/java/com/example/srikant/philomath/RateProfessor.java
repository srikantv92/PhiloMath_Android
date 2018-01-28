package com.example.srikant.philomath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.TelecomManager;
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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RateProfessor extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate_professor);
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		final String EmailID = sharedpreferences.getString("Email", "Not Found");
		final TextView notTeachingCourse = (TextView) findViewById(R.id.notTeachingCourses);
		final ListView professors = (ListView) findViewById(R.id.listProfessors);
		String IP = "https://intense-thicket-93384.herokuapp.com/webapi/GetCourseAndProfessorDetails/" + EmailID;
		try {
			URL url = new URL(IP);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder responseStrBuilder = new StringBuilder();

			String inputStr;
			while ((inputStr = rd.readLine()) != null)
				responseStrBuilder.append(inputStr);
			JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
			final JSONArray results = jsonObject.getJSONArray("results");
			final List<String> courses = new ArrayList<>();
			int j = 0;
			for (; j < results.length(); j++) {
				JSONObject obj = (JSONObject) results.get(j);
				courses.add(obj.getString("course"));
			}

			if (conn.getResponseCode() == 200) {
				if (courses.size() == 0) {
					notTeachingCourse.setVisibility(View.VISIBLE);
				}
				ArrayAdapter adapter2 = new ArrayAdapter<String>(RateProfessor.this, R.layout.list_courses_taught,
						courses);
				professors.setAdapter(adapter2);

				ViewGroup.LayoutParams listViewParams2 = (ViewGroup.LayoutParams) professors.getLayoutParams();
				professors.measure(0, 0);

				listViewParams2.height = (professors.getMeasuredHeight() * (courses.size()));

				professors.requestLayout();

				professors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent submit = new Intent(RateProfessor.this, SubmitRating.class);

						try {
							submit.putExtra("professor", ((JSONObject) results.get(position)).getString("professor"));
							submit.putExtra("easiness", ((JSONObject) results.get(position)).getString("easiness"));
							submit.putExtra("numberOfVotes",
									((JSONObject) results.get(position)).getString("numberOfVotes"));
							submit.putExtra("clarity", ((JSONObject) results.get(position)).getString("clarity"));
							submit.putExtra("rating", ((JSONObject) results.get(position)).getString("rating"));
							submit.putExtra("course", ((JSONObject) results.get(position)).getString("course"));
							submit.putExtra("overall", ((JSONObject) results.get(position)).getString("overall"));
							submit.putExtra("profEmail", ((JSONObject) results.get(position)).getString("profEmail"));
							submit.putExtra("helpfulness",
									((JSONObject) results.get(position)).getString("helpfulness"));
							startActivity(submit);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
