package com.example.srikant.philomath;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Srikant on 2/14/2016.
 */
public class Seq_spinner extends Activity implements AdapterView.OnItemSelectedListener {
	EditText otherSeq;
	RegisterDB registeration;

	public Seq_spinner(EditText editText, RegisterDB registeration) {

		otherSeq = editText;
		this.registeration = registeration;
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

		// An item was selected. You can retrieve the selected item using
		if (parent.getItemAtPosition(pos).toString().equals("Other")) {

			otherSeq.setVisibility(View.VISIBLE);
			registeration.setSecurityQuestion(otherSeq.getText().toString());
		} else {
			otherSeq.setVisibility(View.GONE);
			registeration.setSecurityQuestion(parent.getItemAtPosition(pos).toString());
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}
}
