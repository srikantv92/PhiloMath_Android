package com.example.srikant.philomath;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Srikant on 2/14/2016.
 */
public class Category_spinner extends Activity implements AdapterView.OnItemSelectedListener {
    RegisterDB registration;
    Category c;
    int i;
    public Category_spinner(RegisterDB registration){

        this.registration=registration;
        i=1;
    }
    public Category_spinner(Category c){
        this.c=c;
        i=2;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        if(i==1)
        registration.setCategory(parent.getItemAtPosition(pos).toString());
        else
         c.Category= parent.getItemAtPosition(pos).toString();
       // Toast.makeText(parent.getContext(), "Selected Category is " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}