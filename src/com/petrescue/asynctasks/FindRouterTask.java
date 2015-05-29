package com.petrescue.asynctasks;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.example.whereismytw.MainActivity;
import com.example.whereismytw.rest.GMapV2Direction;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class FindRouterTask extends AsyncTask<LatLng, Void, PolylineOptions>{

	protected static Polyline atual = null;
	private Context context;
	ProgressDialog progressDialog;
	
	public FindRouterTask(Context context){
		
		this.context= context;
	}
	
	@Override
	protected PolylineOptions doInBackground(LatLng... params) {
	
		GMapV2Direction md = new GMapV2Direction();
		org.w3c.dom.Document doc = md.getDocument(params[0], params[1], GMapV2Direction.MODE_DRIVING);
		
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                Color.BLUE);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }
       
		return rectLine;
	}
	
	@Override
	protected void onPostExecute(PolylineOptions result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
			if(result != null){
				
					if(atual != null){
						
						atual.remove();
					}
				
					MainActivity activity = (MainActivity) (context);
					
					atual = activity.getMap().addPolyline(result);
			}
		progressDialog.dismiss();
	}
	
	@Override
	protected void onPreExecute() {
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("buscando rota ... ");
		progressDialog.show();
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}
}
