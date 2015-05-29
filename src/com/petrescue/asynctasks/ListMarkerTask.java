package com.petrescue.asynctasks;

import java.util.HashSet;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.whereismytw.MainActivity;
import com.example.whereismytw.R;
import com.example.whereismytw.model.Publish;
import com.example.whereismytw.rest.manager.Api;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
			
			MainActivity activity = (MainActivity) (context);
			
			for(Publish publish : result){
				
				int drawableResourceId = activity.getResources().getIdentifier(publish.getCategoria().getTag(), "drawable", activity.getPackageName());

				MarkerOptions markerOptions = new MarkerOptions()
		          .position(new LatLng(publish.getLat(), publish.getLng()))
		          .title(publish.getTitle())  
		          .snippet(publish.getContent())
		          .icon(BitmapDescriptorFactory.fromResource(drawableResourceId));

				Marker marker = activity.getMap().addMarker(markerOptions); 
				
				if(!Api.markersMap.containsKey(publish.getCategoria().getTag())){
					
					Api.markersMap.put(publish.getCategoria().getTag(), new HashSet<Marker>());
				}
				
				Api.markersMap.get(publish.getCategoria().getTag()).add(marker);
			}
			
			
		}
		if(result != null && result.isEmpty()){
			
			Toast.makeText(this.context, "Nenhuma publicação foi encontrada", Toast.LENGTH_LONG).show();
		}
		progressDialog.dismiss();
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
