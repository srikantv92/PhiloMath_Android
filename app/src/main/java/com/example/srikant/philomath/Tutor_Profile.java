package com.example.srikant.philomath;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Tutor_Profile extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tutor__profile);

		final TextView tutorName = (TextView) findViewById(R.id.tutorName);
		final TextView tutorEmail = (TextView) findViewById(R.id.tutorEmail);
		final TextView tutorPhone = (TextView) findViewById(R.id.tutorPhone);
		final TextView tutorAddress = (TextView) findViewById(R.id.tutorAddress);
		final TextView tutorAvailability = (TextView) findViewById(R.id.tutorAvailability);
		final TextView tutorPricing = (TextView) findViewById(R.id.tutorPricing);
		final TextView tutorCourse = (TextView) findViewById(R.id.tutorCourse);
		final TextView tutorTravel = (TextView) findViewById(R.id.tutorTravel);

		final String tutorFullName = getIntent().getExtras().getString("name").replace("\n", "");
		tutorName.setText(tutorFullName);
		Log.d("Received in TutProf", tutorFullName);

		final String tutorEmailID = getIntent().getExtras().getString("email");
		tutorEmail.setText(tutorEmailID);

		final String tutorPhoneNum = getIntent().getExtras().getString("phoneNumber");
		tutorPhone.setText(tutorPhoneNum);

		final String tutorFullAddress = getIntent().getExtras().getString("address");
		tutorAddress.setText(tutorFullAddress);

		final String tutorAvailabilityInfo = getIntent().getExtras().getString("availability");
		tutorAvailability.setText(tutorAvailabilityInfo);

		final String tutorPricingInfo = getIntent().getExtras().getString("pricing");
		tutorPricing.setText(tutorPricingInfo);

		final String tutorCourseName = getIntent().getExtras().getString("courseName");
		tutorCourse.setText(tutorCourseName);

		final String tutorTravelInfo = getIntent().getExtras().getString("travel");
		tutorTravel.setText(tutorTravelInfo);

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);
		TextView NumVotes = (TextView) findViewById(R.id.NumOfVotesTutor);
		NumVotes.setText("(" + getIntent().getExtras().getString("numOfVotes") + " Votes)");

		float randomRating = (float) (0 + randomGenerator.nextFloat() * (5.0));
		RatingBar rating = (RatingBar) findViewById(R.id.ratingTutor);
		rating.setRating(Float.parseFloat(getIntent().getExtras().getString("rating")));

		final ImageButton phoneCall = (ImageButton) findViewById(R.id.CallTutor);

		phoneCall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tutorPhoneNum, null));
				startActivity(intent);

			}
		});

		final ImageButton emailTut = (ImageButton) findViewById(R.id.EmailTutor);
		final String mail = tutorFullName + " <" + tutorEmailID + ">";
		emailTut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO).setData(new Uri.Builder().scheme("mailto").build())
						.putExtra(Intent.EXTRA_EMAIL, new String[] { mail }).putExtra(Intent.EXTRA_SUBJECT, " ")
						.putExtra(Intent.EXTRA_TEXT, " ");

				ComponentName emailApp = intent.resolveActivity(getPackageManager());
				ComponentName unsupportedAction = ComponentName.unflattenFromString("com.android.fallback/.Fallback");
				if (emailApp != null && !emailApp.equals(unsupportedAction))
					try {

						Intent chooser = Intent.createChooser(intent, "Send email with");
						startActivity(chooser);
						return;
					} catch (ActivityNotFoundException ignored) {
					}

				Toast.makeText(Tutor_Profile.this, "Couldn't find an email app and account", Toast.LENGTH_LONG).show();
			}
		});

		final ImageButton locateTut = (ImageButton) findViewById(R.id.LocateTutor);

		locateTut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + tutorFullAddress);

				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				Intent chooser = Intent.createChooser(mapIntent, "Navigate with");
				startActivity(chooser);

			}
		});

		final ImageButton smsTut = (ImageButton) findViewById(R.id.smsTutor);

		smsTut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
				sendIntent.putExtra("sms_body", " ");
				sendIntent.putExtra("address", tutorPhoneNum);
				sendIntent.setType("vnd.android-dir/mms-sms");
				startActivity(sendIntent);
			}
		});
	}
}
