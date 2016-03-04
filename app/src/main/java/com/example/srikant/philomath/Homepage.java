package com.example.srikant.philomath;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
      final  String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
        final  String[] courseList = {"Maths","Science"};
        final  String[] coursestaught = {"Maths","Science"};



        final EditText searchBar = (EditText) findViewById(R.id.searchBar);
        final Button search = (Button) findViewById(R.id.searchButton);
        final ListView searchResults = (ListView) findViewById(R.id.searchResults);
        final TextView coursesHeading = (TextView) findViewById(R.id.coursesHeading);
        final TextView orBar=(TextView) findViewById(R.id.orBar);
        final TextView orBar2=(TextView) findViewById(R.id.orBar2);
        final ListView courses = (ListView) findViewById(R.id.coursesEnrolled);
        final EditText searchByCategory = (EditText) findViewById(R.id.searchbyCategory);
        final EditText searchByInstructor = (EditText) findViewById(R.id.searchByInstructor);
        final TextView notEnrolled = (TextView) findViewById(R.id.notEnrolled);
        final TextView searchHeading = (TextView) findViewById(R.id.searchHeading);
        final TextView coursesTaught = (TextView) findViewById(R.id.coursesTaught);
        final ListView listCoursesTaught = (ListView) findViewById(R.id.listCoursesTaught);
        boolean tutor=true;
        coursesHeading.setVisibility(View.VISIBLE);

        searchBar.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        searchResults.setVisibility(View.VISIBLE);
        orBar.setVisibility(View.VISIBLE);
        orBar2.setVisibility(View.VISIBLE);
        searchHeading.setVisibility(View.VISIBLE);
        searchByCategory.setVisibility(View.VISIBLE);
        searchByInstructor.setVisibility(View.VISIBLE);
        if(true){

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
        }


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


            if(courseList.length!=0) {
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


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResults.setVisibility(View.VISIBLE);

                ArrayAdapter adapter = new ArrayAdapter<String>(Homepage.this, R.layout.activity_listview, mobileArray);
                searchResults.setAdapter(adapter);

                ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams) searchResults.getLayoutParams();
                searchResults.measure(0,0);
                listViewParams.height = searchResults.getMeasuredHeight()*mobileArray.length;
                searchResults.requestLayout();

                searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(Homepage.this, "User clicked on Item " + mobileArray[position], Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        }

    }

}
