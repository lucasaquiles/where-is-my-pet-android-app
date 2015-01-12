package com.example.whereismytw;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whereismytw.model.Publish;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petrescue.asynctasks.ListMarkerTask;

public class MainActivity extends Activity implements LocationListener, OnMapLongClickListener, OnMarkerClickListener{
	
	private double LAT = -5.092010800000000000;
	private double LONGI = -42.803759700000000000;
	private GoogleMap map;
	
	private LatLng initPosition = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		if(map == null){
			
			Toast.makeText(this, "deu pau!", Toast.LENGTH_LONG).show();
		}else{
			
			
			LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			
		    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000,0, this);
			
			map.setMyLocationEnabled(true);
			
			
			Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			
			initPosition = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
			
			Marker mk = map.addMarker(new MarkerOptions().position(new LatLng(LAT, LONGI)).title("Teresina").snippet("opa").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(initPosition, 15));
			
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
			
			map.setOnMapLongClickListener(this);
			map.setOnMarkerClickListener(this);
		}
		
		if(map != null){
			new ListMarkerTask(this).execute("");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onMapLongClick(LatLng point) {	
		
		Intent intent = new Intent(this, PublishActivity.class);
		
		intent.putExtra("lat", point.latitude);
		intent.putExtra("lng", point.longitude);
		startActivityForResult(intent,1);
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		return false;
	}
	
	public GoogleMap getMap() {
		return map;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		 if (requestCode == 1) {
		        if(resultCode == RESULT_OK){
		            Publish result = (Publish) data.getSerializableExtra("result");
		            
		            getMap().addMarker(new MarkerOptions()
		          .position(new LatLng(result.getLat(), result.getLng()))
		          .title(result.getTitle())  
		          .snippet(result.getContent())
		          .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));  
		            
		        }
		        if (resultCode == RESULT_CANCELED) {
		            //Write your code if there's no result
		        }
		    }

		
	}
}

