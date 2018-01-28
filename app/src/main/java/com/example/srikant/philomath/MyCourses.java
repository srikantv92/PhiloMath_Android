package com.example.srikant.philomath;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import org.json.JSONArray;
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

public class MyCourses extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_courses);

		final ListView listcourses = (ListView) findViewById(R.id.coursesEnrolled);
		final TextView notEnrolled = (TextView) findViewById(R.id.notEnrolled);
		final ListView listCoursesTaught = (ListView) findViewById(R.id.listCoursesTaught);
		final TextView notTeaching = (TextView) findViewById(R.id.notTeaching);

		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
		final String EmailID = sharedpreferences.getString("Email", "Not Found");

		String IP = "https://intense-thicket-93384.herokuapp.com/webapi/editProfile";
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

			JSONArray student = jsonObject.getJSONArray("coursesTakenAsStudent");
			JSONArray teacher = jsonObject.getJSONArray("courses");
			List<String> coursesTakenAsStudent = new ArrayList<>();
			final List<String> courses = new ArrayList<>();
			if (student != null) {
				for (int i = 0; i < student.length(); i++) {
					coursesTakenAsStudent.add(student.get(i).toString());
				}
			}
			if (teacher != null) {
				for (int i = 0; i < teacher.length(); i++) {
					courses.add(teacher.get(i).toString());
				}
			}

			Log.d("connection response", String.valueOf(conn.getResponseCode()));

			if (conn.getResponseCode() == 200) {
				if (coursesTakenAsStudent.size() == 0) {
					notEnrolled.setVisibility(View.VISIBLE);
				}
				if (courses.size() == 0) {
					notTeaching.setVisibility(View.VISIBLE);
				}
				ArrayAdapter adapter2 = new ArrayAdapter<String>(MyCourses.this, R.layout.list_courses_taught, courses);
				listCoursesTaught.setAdapter(adapter2);

				ViewGroup.LayoutParams listViewParams2 = (ViewGroup.LayoutParams) listCoursesTaught.getLayoutParams();
				listCoursesTaught.measure(0, 0);

				listViewParams2.height = (listCoursesTaught.getMeasuredHeight() * (courses.size()));

				listCoursesTaught.requestLayout();

				listCoursesTaught.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
						Log.d("Long click", "pressed " + position);

						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyCourses.this);
						alertDialogBuilder.setTitle("Want to Delete?");

						// set dialog message
						alertDialogBuilder.setMessage("Click yes to delete!").setCancelable(false)
								.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										// if this button is clicked
										Toast.makeText(MyCourses.this, "yes clicked", Toast.LENGTH_SHORT).show();
										Log.d("alert", "yes clicked");
										String IP = "https://intense-thicket-93384.herokuapp.com/webapi/removeCourseYouTeach";
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
											String message = EmailID + "," + courses.get(position);
											writer.write(message);
											writer.close();
											Log.d("request sent", "request sent " + EmailID);

											BufferedReader rd = new BufferedReader(
													new InputStreamReader(conn.getInputStream()));

											String res = rd.readLine();
											if (conn.getResponseCode() == 200) {
												if (res.contains("success")) {
													Log.d("course removed", "success");
													Intent intent = getIntent();
													finish();
													startActivity(intent);
												}
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
										dialog.cancel();
									}
								}).setNegativeButton("No", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing

										Log.d("alert", "No clicked");
										dialog.cancel();
									}
								});

						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();

						// show it
						alertDialog.show();

						return true;
					}
				});

				ArrayAdapter adapter1 = new ArrayAdapter<String>(MyCourses.this, R.layout.courses_list,
						coursesTakenAsStudent);
				listcourses.setAdapter(adapter1);

				ViewGroup.LayoutParams listViewParams1 = (ViewGroup.LayoutParams) listcourses.getLayoutParams();
				listcourses.measure(0, 0);

				listViewParams1.height = (listcourses.getMeasuredHeight() * (coursesTakenAsStudent.size()));

				listcourses.requestLayout();
			}
		} catch (Exception e) {
			Log.d("Exception ", e.toString());
		}
	}
}
