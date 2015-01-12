package com.example.whereismytw.rest.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.whereismytw.model.Publish;
import com.example.whereismytw.rest.WebService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Api {
	
	private final static String DEFAULT_URL = "http://rescue-a-pet.appspot.com/";	
	
	public boolean addMarker(Publish publish){
		
//		String url = DEFAULT_URL+"api?service=publish&title="+publish.getTitle()+"&content="+publish.getContent()+"&lat="+publish.getLat()+"&lng="+publish.getLng();
		
		WebService webService = new WebService(DEFAULT_URL);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", publish.getTitle());
		params.put("content", publish.getContent());
		params.put("lat", publish.getLat().toString());
		params.put("lng", publish.getLng().toString());

		String response = webService.webGet("", params);
		
		return true;
	}
	
	public List<Publish> listMarkers(){
		
		WebService webService = new WebService(DEFAULT_URL+"api?service=publish_list");
		
		Map<String, String> params = new HashMap<String, String>();
		
		String response = webService.webGet("", params);
		Gson gson = new Gson();
		
		List<Publish> publishes = gson.fromJson(response, new TypeToken<ArrayList<Publish>>(){}.getType());
		
		return publishes;
	}
	
	
}
