package com.petrescue.asynctasks;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.whereismytw.MainActivity;
import com.example.whereismytw.model.Publish;
import com.example.whereismytw.rest.manager.Api;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ListMarkerTask extends AsyncTask<String, Void, List<Publish>>{

	private Context context;
	ProgressDialog progressDialog;
	
	public ListMarkerTask(Context context){
		
		this.context =  context;
	}
	
	
	@Override
	protected List<Publish> doInBackground(String... params) {
		
		return new Api().listMarkers();
	}


	
	@Override
	protected void onPostExecute(List<Publish> result) {
		
		if(result != null && result.size() > 0){
			
			MainActivity activity = (MainActivity) (this.context);
			
			for(Publish publish : result){
				
				activity.getMap().addMarker(new MarkerOptions()
		        .position(new LatLng(publish.getLat(), publish.getLng()))
		        .title(publish.getTitle())           
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));  
				
				super.onPostExecute(result);

				progressDialog.dismiss();
			}
		}
	}
	
	@Override
	protected void onPreExecute() {
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("carregando publicacoes ... ");
		progressDialog.show();
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}
}
