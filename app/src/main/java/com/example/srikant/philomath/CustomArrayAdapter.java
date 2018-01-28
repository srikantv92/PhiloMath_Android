package com.example.srikant.philomath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by Srikant on 3/27/2016.
 */
public class CustomArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> values;

	public CustomArrayAdapter(Context context, List<String> values) {
		super(context, R.layout.activity_listview, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_listview, parent, false);
		TextView courseDetails = (TextView) rowView.findViewById(R.id.label2);
		
		String courseName = values.get(position).split(",")[0];
		courseName = courseName.substring(0, 1).toUpperCase() + courseName.substring(1);
		courseDetails.setText(courseName);
		
		TextView professorName = (TextView) rowView.findViewById(R.id.label3);
		String profName = values.get(position).split(",")[1];
		profName = profName.substring(0, 1).toUpperCase() + profName.substring(1);
		professorName.setText(profName);
		
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);
		TextView NumVotes = (TextView) rowView.findViewById(R.id.NumOfVotes);
		NumVotes.setText("(" + values.get(position).split(",")[3] + " Votes)");

		float randomRating = (float) (0 + randomGenerator.nextFloat() * (5.0));
		RatingBar rating = (RatingBar) rowView.findViewById(R.id.ratingBar);
		rating.setRating(Float.parseFloat(values.get(position).split(",")[2]));
		return rowView;
	}
}
