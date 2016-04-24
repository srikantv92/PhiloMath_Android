package com.example.srikant.philomath;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Register extends AppCompatActivity {

    RegisterDB registration=  new RegisterDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);
        //Are you a tutor?
        final CheckBox tutor = (CheckBox) findViewById(R.id.checkBox);

        //Spinner for Category
        final Spinner category = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tutor_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new Category_spinner(registration));

        //Radio Button for willing to travel
        final RadioButton willingToTravel = (RadioButton) findViewById(R.id.willingToTravel);
        final EditText radius = (EditText) findViewById(R.id.radius);
        willingToTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {

                    radius.setVisibility(View.VISIBLE);
                }
                else {
                    radius.setVisibility(View.GONE);
                }

            }
        });


        //Spinner for securityQuestions
        final Spinner securityQuestions = (Spinner) findViewById(R.id.security);

        ArrayAdapter<CharSequence> seq_adapter = ArrayAdapter.createFromResource(this,
                R.array.security_questions, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        securityQuestions.setAdapter(seq_adapter);
        EditText otherSeq= (EditText) findViewById(R.id.otherSeq);

        securityQuestions.setOnItemSelectedListener(new Seq_spinner(otherSeq,registration));


        final EditText course = (EditText) findViewById(R.id.courseName);
        final EditText availability = (EditText) findViewById(R.id.availability);
        final EditText pricing = (EditText) findViewById(R.id.pricing);
        final TextView selectCatogory = (TextView) findViewById(R.id.selectCategory);

        //If Are you a tutor is checked or unchecked. Change the visibility of course, category, availability
        //pricing depending on it.
        tutor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    selectCatogory.setVisibility(View.VISIBLE);
                    course.setVisibility(View.VISIBLE);
                    category.setVisibility(View.VISIBLE);
                    availability.setVisibility(View.VISIBLE);
                    pricing.setVisibility(View.VISIBLE);
                }
                else{
                    selectCatogory.setVisibility(View.GONE);
                    course.setVisibility(View.GONE);
                    category.setVisibility(View.GONE);
                    availability.setVisibility(View.GONE);
                    pricing.setVisibility(View.GONE);
                }
            }
        });

        final EditText fullName = (EditText) findViewById(R.id.fullName);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText phoneNum = (EditText) findViewById(R.id.phoneNumber);
        final EditText address = (EditText) findViewById(R.id.address);
        final EditText city= (EditText) findViewById(R.id.city);
        final EditText state = (EditText) findViewById(R.id.state);
        final EditText country = (EditText) findViewById(R.id.country);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        final EditText answer = (EditText) findViewById(R.id.answerSeq);







        //Register Button
        Button register = (Button) findViewById(R.id.register);

        final String[] status = new String[1];
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                // "^[a-zA-Z0-9]+$"

                String pass="^[a-zA-Z0-9]+$";

                java.util.regex.Pattern p1 = java.util.regex.Pattern.compile(pass);
                java.util.regex.Matcher m1 = p1.matcher(password.getText().toString());



                String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                java.util.regex.Matcher m = p.matcher(email.getText().toString());
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(Register.this, "Passwords don't match!!", Toast.LENGTH_LONG).show();
                } else if (fullName.getText().toString().isEmpty() || password.getText().toString().isEmpty() || email.getText().toString().isEmpty() || address.getText().toString().isEmpty() || answer.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                }
                else if(password.getText().toString().length()<6){
                    Toast.makeText(Register.this, "Password should contain minimum 6 characters", Toast.LENGTH_LONG).show();
                }
                else if(!m1.matches()){
                    Toast.makeText(Register.this, "Please enter valid Password", Toast.LENGTH_LONG).show();
                }
                else if(phoneNum.getText().toString().length()<10){
                    Toast.makeText(Register.this, "Please valid Phone Number", Toast.LENGTH_LONG).show();
                }
                else if(!m.matches()){
                    Toast.makeText(Register.this, "Please enter valid Email", Toast.LENGTH_LONG).show();
                }

                else if (tutor.isChecked()) {
                    if (course.getText().toString().isEmpty() || availability.getText().toString().isEmpty() || pricing.getText().toString().isEmpty()) {
                        Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                    } else {
                        // new RegisterDB(,,, , ,seqQuest, ,,,, )
                        registration.setEmail(email.getText().toString());
                        registration.setPassword(password.getText().toString());
                        registration.setFullName(fullName.getText().toString());
                        registration.setPhoneNumber(phoneNum.getText().toString());
                        registration.setAnswer(answer.getText().toString());
                        registration.setTutor(tutor.isChecked());
                        registration.setAddress(address.getText().toString()+","+city.getText().toString()+","+state.getText().toString()+","+country.getText().toString());
                        registration.setTravel(willingToTravel.isChecked());
                        final String distance;
                        if (willingToTravel.isChecked()) {

                            distance = radius.getText().toString();
                        } else {

                            distance = "N/A";
                        }
                        registration.setRadius(distance);


                        registration.setCourse(course.getText().toString());
                        registration.setAvailability(availability.getText().toString());
                        registration.setPricing(pricing.getText().toString());


                        String a = registration.insertData(getResources().getString(R.string.IpAddress));
                        Toast.makeText(Register.this, a, Toast.LENGTH_LONG).show();
                        registration.printall();
                        if(a.contains("successful")){
                            Intent success = new Intent(Register.this, Homepage.class);
                            startActivity(success);
                        }


                    }
                } else {
                    registration.setEmail(email.getText().toString());
                    registration.setPassword(password.getText().toString());
                    registration.setFullName(fullName.getText().toString());
                    registration.setPhoneNumber(phoneNum.getText().toString());
                    registration.setAnswer(answer.getText().toString());
                    registration.setTutor(tutor.isChecked());
                    registration.setAddress(address.getText().toString()+","+city.getText().toString()+","+state.getText().toString()+","+country.getText().toString());
                    registration.setTravel(willingToTravel.isChecked());
                    final String distance;
                    if (willingToTravel.isChecked()) {

                        distance = radius.getText().toString();
                    } else {

                        distance = "N/A";
                    }
                    registration.setRadius(distance);


                    String a = registration.insertData(getResources().getString(R.string.IpAddress));
                    Toast.makeText(Register.this, a, Toast.LENGTH_LONG).show();
                    registration.printall();
                    if(a.contains("successful")){
                        Intent success = new Intent(Register.this, Homepage.class);
                        startActivity(success);
                    }

                }

            }
        });





    }

}
