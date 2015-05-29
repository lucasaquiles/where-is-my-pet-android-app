package com.example.whereismytw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.whereismytw.model.Publish;
import com.example.whereismytw.rest.manager.Api;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petrescue.asynctasks.FindRouterTask;
import com.petrescue.asynctasks.ListMarkerTask;

public class MainActivity extends ActionBarActivity implements LocationListener, OnMapLongClickListener, OnMarkerClickListener, OnNavigationListener{
	
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
			
//			Marker mk = map.addMarker(new MarkerOptions().position(new LatLng(LAT, LONGI)).title("Teresina").snippet("opa").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(initPosition, 15));

//			CircleOptions circleOptions = new CircleOptions()
//			.strokeWidth(1)
//			.strokeColor(Color.BLACK)
//		    .center(initPosition).radius(30);

//			Circle circle = map.addCircle(circleOptions);
			
			map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
			
			map.setOnMapLongClickListener(this);
			map.setOnMarkerClickListener(this);
			
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(initPosition)
		    .zoom(17)                   
		    .bearing(90)                
		    .tilt(30)                   
		    .build();                   
			
			map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
		
		if(map != null){
			new ListMarkerTask(this).execute("");
		}
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		ArrayList<String> itemList = new ArrayList<String>();
		
		itemList.add("Todos");
		itemList.add("Cachorros para adoção");
		itemList.add("Gatos par adoção");
		itemList.add("ONGs");
		itemList.add("Gatos");
		itemList.add("Cachorros");
		
		ArrayAdapter<String> aAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
		
		getSupportActionBar().setListNavigationCallbacks(aAdpt, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

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
		
		LatLng destino = marker.getPosition();
		
		LatLng origin = getMyCurrentPosition();
		
		new FindRouterTask(this).execute(destino, origin);
		
		return false;
	}

	private LatLng getMyCurrentPosition() {
		LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
	    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000,0, this);
		
		map.setMyLocationEnabled(true);
		
		Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		return new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
	}
	
	public GoogleMap getMap() {
		return map;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		 if (requestCode == 1) {
		        if(resultCode == RESULT_OK){
		            Publish result = (Publish) data.getSerializableExtra("result");
		            
		            int drawableResourceId = this.getResources().getIdentifier(result.getCategoria().getTag(), "drawable", this.getPackageName());
		            
		            Marker marker = getMap().addMarker(new MarkerOptions()
		          .position(new LatLng(result.getLat(), result.getLng()))
		          .title(result.getTitle())  
		          .snippet(result.getContent())
		          .icon(BitmapDescriptorFactory.fromResource(drawableResourceId)));  
		            
		            if(!Api.markersMap.containsKey(result.getCategoria().getTag())){
						
						Api.markersMap.put(result.getCategoria().getTag(), new HashSet<Marker>());
					}
					
					Api.markersMap.get(result.getCategoria().getTag()).add(marker);
		            
		        }
		        
		        if (resultCode == RESULT_CANCELED) {
		            //Write your code if there's no result
		        }
		    }
 	}
	
	public void enableMarkerView(String targetKey){
		
		Map<String, Set<Marker>> markersMap = Api.markersMap;
		boolean nTemNenhum = false;
		for(String key : markersMap.keySet()){
			
			Set<Marker> set = markersMap.get(key);
			
			if(set != null){
				for(Marker m : set){

					if(key.equalsIgnoreCase(targetKey)){
						
						if(!m.isVisible()){
							m.setVisible(true);
						}
					}else{
						m.setVisible(false);
					}
				}
			}
		}
		
		
		if(!markersMap.keySet().contains(targetKey)){
			nTemNenhum = true;
		}
		
		if(nTemNenhum){
			Toast.makeText(this, "Nenhum item para a categoria selecionada", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		
		Map<String, Set<Marker>> markersMap = Api.markersMap;
		
		if(arg0 == 0){
			
			for(String key : markersMap.keySet()){
				
				for(Marker m : markersMap.get(key)){
					
					m.setVisible(true);
				}
			} 
			
			
		}

		if(arg0 == 1){
			
			enableMarkerView("ic_adopt_dog");
		}

		if(arg0 == 2){
			enableMarkerView("ic_adopt_cat");
		}
		
		if(arg0 == 3){
			
			enableMarkerView("ic_ong");
		}
		
		if(arg0 == 4){
			
			enableMarkerView("ic_cat");
		}
		
		if(arg0 == 5){
			
			enableMarkerView("ic_dog");
		}
		
		return false;
	}
}

