package com.example.whereismytw.rest.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.whereismytw.model.Categoria;
import com.example.whereismytw.model.Publish;
import com.example.whereismytw.rest.WebService;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Api {
	
	private final static String DEFAULT_URL = "http://rescue-a-pet.appspot.com/";	
	
	public static Set<Marker> markers = new HashSet<Marker>();
	public static Map<String, Set<Marker>> markersMap = new HashMap<String, Set<Marker>>();
	
	public boolean addMarker(Publish publish){
		
		WebService webService = new WebService(DEFAULT_URL+"api");
		
		Map<String, String> params = new HashMap<String, String>();
		
		//service=publish
		params.put("service", "publish");
		params.put("title", publish.getTitle());
		params.put("content", publish.getContent());
		params.put("lat", publish.getLat().toString());
		params.put("lng", publish.getLng().toString());
		params.put("categoria", publish.getCategoria().getId());

		String response = webService.webGet("", params);
		
		return true;
	}
	
	public List<Publish> listMarkers(){
		
		WebService webService = new WebService(DEFAULT_URL+"api");
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("service", "publish_list");
		
		String response = webService.webGet("", params);
		Gson gson = new Gson();
		
		List<Publish> publishes = gson.fromJson(response, new TypeToken<ArrayList<Publish>>(){}.getType());
		
		return publishes;
	}
	
	public ArrayList<Categoria> listCategories(){
		
		WebService webService = new WebService(DEFAULT_URL+"api?service=category_list");
		
		Map<String, String> params = new HashMap<String, String>();
		
		String response = webService.webGet("", params);
		Gson gson = new Gson();
		
		ArrayList<Categoria> categorias = gson.fromJson(response, new TypeToken<ArrayList<Categoria>>(){}.getType());
		
		return categorias;
	}
	
}
