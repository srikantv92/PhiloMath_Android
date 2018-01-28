package com.example.srikant.philomath;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecurityQuestion extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_question);
		final EditText seqAns = (EditText) findViewById(R.id.answerSeqQues);
		TextView seqQues = (TextView) findViewById(R.id.enterSeq);
		seqQues.setText(getIntent().getExtras().getString("seq"));
		Button seqSubmit = (Button) findViewById(R.id.submitSeq);
		seqSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

				String ans = seqAns.getText().toString();
				if (ans.equals(getIntent().getExtras().getString("ans"))) {
					Toast.makeText(SecurityQuestion.this, "Success!! Please reset your password", Toast.LENGTH_LONG)
							.show();
					Intent successSeq = new Intent(SecurityQuestion.this, ResetPassword.class);
					successSeq.putExtra("email", getIntent().getExtras().getString("email"));
					startActivity(successSeq);

				} else {
					Toast.makeText(SecurityQuestion.this, "Failed in retriving your information", Toast.LENGTH_LONG)
							.show();
				}

			}
		});
	}
}
