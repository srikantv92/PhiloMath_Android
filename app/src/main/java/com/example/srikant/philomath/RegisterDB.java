package com.example.srikant.philomath;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
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


/**
 * Created by Srikant on 2/14/2016.
 */
public class RegisterDB {
    String fullName;
    String email;
    String phoneNumber;
    String address;
    String password;
    String securityQuestion;
    String answer;
    boolean travel;
    String radius;
    boolean tutor;
    String course;
    String category;
    String availability;
    String pricing;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public boolean isTutor() {
        return tutor;
    }

    public void setTutor(boolean tutor) {
        this.tutor = tutor;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public void printall(){
        Log.d("Registeration","full name "+fullName+"  "+" pass "+password+ " price "+ pricing+" catogory "+category+" seqQues "+securityQuestion+" travel "+ travel);
    }

    public String insertData(String IPAd) {
        String status="";
        try {
            String IP =  "https://intense-thicket-93384.herokuapp.com/webapi/register";
            URL url = new URL(IP );
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type",
                    "application/json");

            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");

            JSONObject obj = new JSONObject();
            obj.put("fullName", fullName);
            obj.put("email", email);
            obj.put("phoneNumber", phoneNumber);
            obj.put("address", address);
            obj.put("password", password);
            obj.put("securityQuestion", securityQuestion);
            obj.put("answer", answer);
            obj.put("travel", travel);
            obj.put("radius", radius);
            obj.put("tutor", tutor);
            obj.put("course", course);
            obj.put("category", category);
            obj.put("availability", availability);
            obj.put("pricing", pricing);
            writer.write(obj.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String response = rd.readLine();
            Log.d("status",response);
            if (conn.getResponseCode() == 200) {
                if (response.contains("duplicate")) {
                   //user has already registered

                    status="User has already registered";

                    Log.d("status","User has already registered");

                } else {
                    //successful
                    status="Registration successful";
                    Log.d("status",status);
                }
            } else {
                //unsuccessful
                status="Registration failed";
                Log.d("status",status);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
return status;
    }


}
