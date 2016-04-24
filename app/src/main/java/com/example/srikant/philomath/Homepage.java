package com.example.srikant.philomath;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       // getActionBar().setDisplayHomeAsUpEnabled(false);
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String EmailID = sharedpreferences.getString("Email", "Not Found");
        if (EmailID.equals("Not Found")) {

            Toast.makeText(Homepage.this, "Session is expired. Please Login!", Toast.LENGTH_LONG).show();

            Intent main = new Intent(Homepage.this,MainActivity.class);
            startActivity(main);
        }

        final EditText searchBar = (EditText) findViewById(R.id.searchBar);
        final Button search = (Button) findViewById(R.id.searchButton);
        final ListView searchResults = (ListView) findViewById(R.id.searchResults);
        final TextView coursesHeading = (TextView) findViewById(R.id.coursesHeading);
        final TextView orBar=(TextView) findViewById(R.id.orBar);
        final TextView orBar2=(TextView) findViewById(R.id.orBar2);
       // final ListView courses = (ListView) findViewById(R.id.coursesEnrolled);
        final EditText searchByCategory = (EditText) findViewById(R.id.searchbyCategory);
        final EditText searchByInstructor = (EditText) findViewById(R.id.searchByInstructor);
        //final TextView notEnrolled = (TextView) findViewById(R.id.notEnrolled);
        final TextView searchHeading = (TextView) findViewById(R.id.searchHeading);
        //final TextView coursesTaught = (TextView) findViewById(R.id.coursesTaught);
        //final ListView listCoursesTaught = (ListView) findViewById(R.id.listCoursesTaught);
        boolean tutor=true;


        searchBar.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        searchResults.setVisibility(View.VISIBLE);
        orBar.setVisibility(View.VISIBLE);
        orBar2.setVisibility(View.VISIBLE);
        searchHeading.setVisibility(View.VISIBLE);
        searchByCategory.setVisibility(View.VISIBLE);
        searchByInstructor.setVisibility(View.VISIBLE);
       /* if(true){

            coursesTaught.setVisibility(View.VISIBLE);
            listCoursesTaught.setVisibility(View.VISIBLE);





            ArrayAdapter adapter2 = new ArrayAdapter<String>(Homepage.this, R.layout.list_courses_taught, coursestaught);
            listCoursesTaught.setAdapter(adapter2);

            ViewGroup.LayoutParams listViewParams2 = (ViewGroup.LayoutParams) listCoursesTaught.getLayoutParams();
            listCoursesTaught.measure(0, 0);

            listViewParams2.height = (listCoursesTaught.getMeasuredHeight() * (coursestaught.length));

            listCoursesTaught.requestLayout();
        }
        else{
           // coursesHeading.setPadding(0,75,0,0);
        }*/


        if(true){
           /* coursesHeading.setVisibility(View.VISIBLE);
            searchBar.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            searchResults.setVisibility(View.VISIBLE);
            orBar.setVisibility(View.VISIBLE);
            orBar2.setVisibility(View.VISIBLE);
            searchHeading.setVisibility(View.VISIBLE);
            searchByCategory.setVisibility(View.VISIBLE);
            searchByInstructor.setVisibility(View.VISIBLE);*/


          /*  if(courseList.length!=0) {
                courses.setVisibility(View.VISIBLE);
                ArrayAdapter adapter1 = new ArrayAdapter<String>(Homepage.this, R.layout.courses_list, courseList);
                courses.setAdapter(adapter1);

                ViewGroup.LayoutParams listViewParams1 = (ViewGroup.LayoutParams) courses.getLayoutParams();
                courses.measure(0, 0);

                listViewParams1.height = (courses.getMeasuredHeight() * (courseList.length));

                courses.requestLayout();
            }
            else{
                notEnrolled.setVisibility(View.VISIBLE);

            }
*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                final List<String> results = new ArrayList<String>();
                String IP = "https://intense-thicket-93384.herokuapp.com/webapi/searchCourses";
                JSONArray response = null;
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
                    String request = "";
                    boolean abc=false;
                    if (!searchBar.getText().toString().isEmpty()) {
                        request = request + "course" + "," + searchBar.getText().toString();
                        abc=true;
                    }
                    if (!searchByCategory.getText().toString().isEmpty()) {
                        if(abc) {
                            request = request + "," + "category" + "," + searchByCategory.getText().toString();
                        }
                        else{
                            abc=true;
                            request = request + "category" + "," + searchByCategory.getText().toString();
                        }
                    } if (!searchByInstructor.getText().toString().isEmpty()) {
                        if(abc){
                            request = request + "," + "professor" + "," + searchByInstructor.getText().toString();
                        }
                        else{
                            request = request + "professor" + "," + searchByInstructor.getText().toString();
                            abc=true;
                        }
                    }
                    Log.d("Search request ",request);
                    writer.write(request);
                    writer.close();
                    BufferedReader rd = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    StringBuilder responseStrBuilder = new StringBuilder();

                    String inputStr;
                    while ((inputStr = rd.readLine()) != null)
                        responseStrBuilder.append(inputStr);
                    JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

                    response = jsonObject.getJSONArray("searchResults");
                    Log.d("response search", (String) ((JSONObject)response.get(0)).get("courseName"));
                    if (conn.getResponseCode() == 200) {
                        int len = response.length() - 1;
                        while (len >= 0) {
                            JSONObject obj = (JSONObject) response.get(len);
                            String searchResult = obj.getString("courseName") + "," + obj.getString("professorName").replace("\n", "")+","+obj.getString("rating")+","+obj.getString("numOfVotes");
                            results.add(searchResult);
                            len--;
                        }
                    }
                } catch (Exception e) {

                    Toast.makeText(Homepage.this, "Error. Please check the data entered!", Toast.LENGTH_LONG).show();
                }
                if (!(results.size() < 1))
                {
                    searchResults.setVisibility(View.VISIBLE);

               /* ArrayAdapter adapter = new ArrayAdapter<String>(Homepage.this, R.layout.activity_listview, results);
                searchResults.setAdapter(adapter);

                ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams) searchResults.getLayoutParams();
                searchResults.measure(0, 0);
                listViewParams.height = searchResults.getMeasuredHeight() * results.size();
                searchResults.requestLayout();*/
                    CustomArrayAdapter adapter = new CustomArrayAdapter(Homepage.this,results);
                    searchResults.setAdapter(adapter);

                    ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams) searchResults.getLayoutParams();
                    searchResults.measure(0, 0);
                    listViewParams.height = searchResults.getMeasuredHeight() * results.size();
                    searchResults.requestLayout();

                    final JSONArray finalResponse = response;
                    searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       // Toast.makeText(Homepage.this, "User clicked on Item " + results.get(position), Toast.LENGTH_LONG).show();
                        Intent tutProf = new Intent(Homepage.this,Tutor_Profile.class);
                        try {
                            JSONObject prof= (JSONObject) finalResponse.get(finalResponse.length()-position-1);
                            tutProf.putExtra("name",prof.getString("professorName"));
                            tutProf.putExtra("email",prof.getString("professorEmail"));
                            tutProf.putExtra("pricing",prof.getString("pricing"));
                            tutProf.putExtra("phoneNumber",prof.getString("phoneNumber"));
                            tutProf.putExtra("courseName",prof.getString("courseName"));
                            tutProf.putExtra("availability",prof.getString("availability"));
                            tutProf.putExtra("address",prof.getString("address"));
                            tutProf.putExtra("travel",prof.getString("travel"));
                            tutProf.putExtra("rating",prof.getString("rating"));
                            tutProf.putExtra("numOfVotes",prof.getString("numOfVotes"));

                            Log.d("Tutor Name in Home Page", prof.getString("professorName"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(tutProf);
                    }
                });
            }
                else{
                    Toast.makeText(Homepage.this, "No Results Found" , Toast.LENGTH_LONG).show();

                }
            }
        });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.logout){
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent logout = new Intent(Homepage.this,MainActivity.class);
            startActivity(logout);

        }

        if(id==R.id.viewCourses){

            Intent mycourses =  new Intent(Homepage.this,MyCourses.class);
            startActivity(mycourses);

        }
        if(id==R.id.rateProfessor){
            Intent rate = new Intent(Homepage.this,RateProfessor.class);
            startActivity(rate);

        }

        if(id==R.id.editProfile){
           Intent editProfile = new Intent(Homepage.this,EditProfile.class);
            startActivity(editProfile);

        }
        if(id==R.id.advt){
            Intent adv = new Intent(Homepage.this,Advertise.class);
            startActivity(adv);
        }
        return true;
    }

    public void onBackPressed() {



        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        startActivity(intent);
    }

}
