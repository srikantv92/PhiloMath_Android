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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditProfile extends AppCompatActivity {
	private Category c = new Category();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);

		final EditText editAddress = (EditText) findViewById(R.id.editAddress);
		final EditText editName = (EditText) findViewById(R.id.editFullName);
		final EditText editPassword = (EditText) findViewById(R.id.editPassword);
		final EditText confirmPass = (EditText) findViewById(R.id.editConfirmPassword);
		final EditText phone = (EditText) findViewById(R.id.editPhoneNumber);
		final EditText city = (EditText) findViewById(R.id.editCity);
		final EditText state = (EditText) findViewById(R.id.editState);
		final EditText country = (EditText) findViewById(R.id.editCountry);
		final EditText newPass = (EditText) findViewById(R.id.editNewPassword);
		final EditText newCourse = (EditText) findViewById(R.id.editCourse);

		final Button editAddressButton = (Button) findViewById(R.id.editAddressButton);
		final Button editNameButton = (Button) findViewById(R.id.editFullNameButton);
		final Button editPasswordButton = (Button) findViewById(R.id.editPasswordButton);
		final Button phoneButton = (Button) findViewById(R.id.editPhoneNumberButton);
		final Button submitChanges = (Button) findViewById(R.id.submitChanges);
		final Button addCourse = (Button) findViewById(R.id.editCourseButton);
		final EditText Availability = (EditText) findViewById(R.id.selectAvailability);
		final EditText Pricing = (EditText) findViewById(R.id.selectPricing);

		final Spinner addCategory = (Spinner) findViewById(R.id.selectCategoryAddCourse);

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
			final JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

			final String Name = jsonObject.getString("name");
			final String phoneNum = jsonObject.getString("phoneNum");
			final String address = jsonObject.getString("address");
			final String[] finalAddress = address.split(",");

			editAddressButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					editAddress.setText(finalAddress[0]);
					editAddressButton.setEnabled(false);
					editAddress.setEnabled(true);
					city.setEnabled(true);
					state.setEnabled(true);
					country.setEnabled(true);
					city.setText(finalAddress[1]);
					state.setText(finalAddress[2]);
					country.setText(finalAddress[3]);
				}
			});

			editNameButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					editNameButton.setEnabled(false);
					editName.setText(Name);
					editName.setEnabled(true);
				}
			});

			addCourse.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addCourse.setEnabled(false);
					newCourse.setEnabled(true);

					Availability.setVisibility(View.VISIBLE);
					Pricing.setVisibility(View.VISIBLE);

					addCategory.setVisibility(View.VISIBLE);

					ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditProfile.this,
							R.array.tutor_categories, android.R.layout.simple_spinner_item);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					addCategory.setAdapter(adapter);
					addCategory.setOnItemSelectedListener(new Category_spinner(c));

				}
			});

			editPasswordButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					editPasswordButton.setEnabled(false);
					editPassword.setEnabled(true);
					editPassword.setHint("Enter Old Password");
					confirmPass.setVisibility(View.VISIBLE);
					newPass.setVisibility(View.VISIBLE);
				}
			});

			phoneButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					phoneButton.setEnabled(false);
					phone.setText(phoneNum);
					phone.setEnabled(true);
				}
			});

			submitChanges.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

					String pas = newPass.getText().toString();
					try {
						if ((!editPassword.getText().toString().equals(jsonObject.getString("password")))
								&& (!pas.isEmpty())) {
							Toast.makeText(EditProfile.this, "Password you entered dosen't match with existing records",
									Toast.LENGTH_LONG).show();
						}

						else if ((!newPass.getText().toString().equals(confirmPass.getText().toString()))
								&& (!pas.isEmpty())) {
							Toast.makeText(EditProfile.this, "Passwords dosen't match!", Toast.LENGTH_LONG).show();

						} else {
							String IP = "https://intense-thicket-93384.herokuapp.com/webapi/updateProfile";
							try {
								URL url = new URL(IP);
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setRequestMethod("POST");
								conn.setDoOutput(true);
								conn.setDoInput(true);
								conn.setUseCaches(false);
								conn.setAllowUserInteraction(false);
								conn.setRequestProperty("Content-Type", "application/json");

								OutputStream out = conn.getOutputStream();
								Writer writer = new OutputStreamWriter(out, "UTF-8");
								JSONObject request = new JSONObject();
								request.put("email", EmailID);
								String Name = editName.getText().toString();
								if (Name.isEmpty()) {
									Name = jsonObject.getString("name");
								}
								request.put("name", Name);
								String address = editAddress.getText().toString() + "," + city.getText().toString()
										+ "," + state.getText().toString() + "," + country.getText().toString();
								if (address.length() < 5) {
									address = jsonObject.getString("address");
								}
								request.put("address", address);
								String Num = phone.getText().toString();
								if (Num.isEmpty()) {
									Num = jsonObject.getString("phoneNum");
								}
								request.put("phoneNum", Num);
								String pass = newPass.getText().toString();
								if (pass.isEmpty()) {
									pass = jsonObject.getString("password");
								}
								request.put("password", pass);

								writer.write(request.toString());
								writer.close();
								out.close();
								BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
								String response = rd.readLine();
								if (conn.getResponseCode() == 200) {
									if (response.contains("success")) {
										Toast.makeText(EditProfile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
										Intent home = new Intent(EditProfile.this, Homepage.class);
										startActivity(home);

									}
								} else {
									Toast.makeText(EditProfile.this, "Problem in connecting server", Toast.LENGTH_LONG)
											.show();
								}
							} catch (Exception e) {
							}
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					String course = newCourse.getText().toString();
					if (!course.isEmpty()) {
						String IP = "https://intense-thicket-93384.herokuapp.com/webapi/addCourseYouTeach";
						try {
							if (c.Category.equals("Select Category")) {
								Toast.makeText(EditProfile.this, "Please Select Category", Toast.LENGTH_SHORT).show();
							} else {
								URL url = new URL(IP);
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setRequestMethod("POST");
								conn.setDoOutput(true);
								conn.setDoInput(true);
								conn.setUseCaches(false);
								conn.setAllowUserInteraction(false);
								conn.setRequestProperty("Content-Type", "application/json");

								OutputStream out = conn.getOutputStream();
								Writer writer = new OutputStreamWriter(out, "UTF-8");
								String avail = Availability.getText().toString();
								if (avail.isEmpty()) {
									avail = "Availability information is not available";
								}

								String pricing = Pricing.getText().toString();
								if (pricing.isEmpty()) {
									pricing = "Pricing information is not available";
								}
								JSONObject addCourse = new JSONObject();
								addCourse.put("email", EmailID);
								addCourse.put("course", course);
								addCourse.put("category", c.Category);
								addCourse.put("pricing", pricing);
								addCourse.put("availability", avail);
								writer.write(addCourse.toString());
								writer.close();
								out.close();
								BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
								String response = rd.readLine();
								if (conn.getResponseCode() == 200) {
									if (response.contains("success")) {
										Toast.makeText(EditProfile.this, "Courses Updated", Toast.LENGTH_SHORT);
										Intent coursesUpdated = new Intent(EditProfile.this, MyCourses.class);
										startActivity(coursesUpdated);
									}
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			});
		} catch (Exception e) {
			Log.d("Exception ", e.toString());
		}
	}
}
