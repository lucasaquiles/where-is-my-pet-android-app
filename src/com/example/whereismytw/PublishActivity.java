package com.example.whereismytw;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.whereismytw.model.Publish;
import com.petrescue.asynctasks.AddMarkerAsyncTask;

public class PublishActivity extends Activity implements OnClickListener{
	
	private EditText editTextTitle;
	private EditText editTextContet;
	private Spinner typePubish;
	private Button button;
	
	private Double lat;
	private Double lng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.publish_view);
		
		super.onCreate(savedInstanceState);
		
		editTextTitle = (EditText) findViewById(R.id.editTextTitle);
		editTextContet = (EditText) findViewById(R.id.editTextContent);
		
		button = (Button) findViewById(R.id.buttonSubmit);
		button.setOnClickListener(this);
		
		
		Intent i = getIntent();
		lat = i.getDoubleExtra("lat", 0);
		lng = i.getDoubleExtra("lng", 0);
		
	}

	@Override
	public void onClick(View v) {
		
		Publish publish = new Publish();
		publish.setLat(lat);
		publish.setLng(lng);
		publish.setTitle(editTextTitle.getText().toString());
		publish.setContent(editTextContet.getText().toString());
//		
		new AddMarkerAsyncTask(this).execute(publish);
		
//		Intent returnIntent = new Intent();
//		returnIntent.putExtra("result", publish);
//		setResult(RESULT_OK,returnIntent);
//		finish();
	}
}

