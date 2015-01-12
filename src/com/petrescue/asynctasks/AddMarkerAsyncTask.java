package com.petrescue.asynctasks;

import android.app.Activity;
import android.app.Notification.Action;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;

import com.example.whereismytw.MainActivity;
import com.example.whereismytw.PublishActivity;
import com.example.whereismytw.model.Publish;
import com.example.whereismytw.rest.manager.Api;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddMarkerAsyncTask extends AsyncTask<Publish, Void, Publish>{

	private Context context;
	ProgressDialog progressDialog;
	
	public AddMarkerAsyncTask(Context context){
		
		this.context = context;
	}
	
	@Override
	protected Publish doInBackground(Publish... params) {
		
		boolean addMarker = new Api().addMarker(params[0]);
		
		return params[0];
	}

	@Override
	protected void onPostExecute(Publish result) {
	 
		progressDialog.dismiss();
	
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", result);
		((PublishActivity) context).setResult(Activity.RESULT_OK,returnIntent);
		
		((PublishActivity) context).finish();
	}
	
	@Override
	protected void onPreExecute() {
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Enviando ... ");
		progressDialog.show();
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}
}
