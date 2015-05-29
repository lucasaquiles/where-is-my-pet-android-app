package com.petrescue.asynctasks;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.whereismytw.PublishActivity;
import com.example.whereismytw.model.Categoria;
import com.example.whereismytw.rest.manager.Api;

public class CategoryListTask extends AsyncTask<String, Void, ArrayList<Categoria>>{

	private Context context;
	private ProgressDialog progressDialog;
	private ArrayList<Categoria> categorias = new ArrayList<Categoria>();
	
	public CategoryListTask(Context context){
		this.context = context;
	}
	
	@Override
	protected ArrayList<Categoria> doInBackground(String... params) {

		ArrayList<Categoria> listCategories = new Api().listCategories();
		return listCategories;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Categoria> result) {

		Spinner typePubish = ((PublishActivity) (context)).getTypePubish();
		if(result != null && !result.isEmpty()){
			
			typePubish.setAdapter(new ArrayAdapter<Categoria>(context, android.R.layout.simple_list_item_1, result));
		}
		
		if(result == null || result.isEmpty()){
			
			typePubish.setAdapter(new ArrayAdapter<Categoria>(context, android.R.layout.simple_list_item_1, new ArrayList<Categoria>()));
		}
		
		progressDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		
		progressDialog = new ProgressDialog(((PublishActivity) (context)));
		progressDialog.setMessage("carregando categorias...");
		progressDialog.show();
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}
}
