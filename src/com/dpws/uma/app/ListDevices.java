package com.dpws.uma.app;

import java.util.ArrayList;

import org.ws4d.java.client.SearchManager;
import org.ws4d.java.types.LocalizedString;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListDevices extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    public ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MUCH TIMES BUTTON WAS CLICKED
    int clickCounter=0;
    
	public SearchClient client;
	public SearchParameter search;
	ListView lista;
	
	private ProgressDialog pd = null;
	private Handler mHandler = new Handler();
	public static QNameSet namesSrv = new QNameSet();
	
	public final static String namespace = "http://www.gisum.uma.es/jmeds";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.listdevices);
        adapter=new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            listItems);
        setListAdapter(adapter);
        
        SearchClient.listDevices.clear();
        SearchClient.listServices.clear();
        
        lista = (ListView)findViewById(android.R.id.list);
        lista.setClickable(true);
        
        pd = ProgressDialog.show(this, "Wait please", "Searching for devices...", true, false);
        
        mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i=0; i<SearchClient.listDevices.size(); i++){
        			if(!listItems.contains(SearchClient.listDevices.get(i).getFriendlyName(LocalizedString.LANGUAGE_EN)))
        				listItems.add(SearchClient.listDevices.get(i).getFriendlyName(LocalizedString.LANGUAGE_EN));
        		}
                
                adapter.notifyDataSetChanged();
                pd.dismiss();
            }}, 3000);
          
       //while (!SearchClient.endSearch){}
       
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        
          @Override
          public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
          	
        	SearchClient.listServices.clear();  
        	
          	client = new SearchClient();
            search = new SearchParameter();
          	
            search.setDeviceTypes (new QNameSet ( new QName (
              		SearchClient.listDevices.get(position).getFriendlyName(
              				LocalizedString.LANGUAGE_EN) , namespace)));
                      
            //namesSrv.add(new QName("AttachmentServices", namespace));
            namesSrv.add(new QName("BasicServices", namespace));
            
            search.setServiceTypes(new QNameSet(namesSrv));    
            
            //SearchManager.searchDevice(search, client, null);
      		SearchManager.searchService (search, client);
          	  
            changeActivity(position);   	
          }
        });
    }
    
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true; /** true -> el men� ya est� visible */
     }
    
     @Override public boolean onOptionsItemSelected(MenuItem item) {
    	 search();
    	 
    	 return true; /** true -> consumimos el item, no se propaga*/
     }
    
    public void changeActivity(int position){
		Intent i = new Intent(this, ListServices.class);
		i.putExtra("item", position);
		startActivity(i);
    	
    }
    
    public void search(){
    	listItems.clear();
    	SearchClient.listDevices.clear();
        SearchClient.listServices.clear();
        
        client = new SearchClient();
		search = new SearchParameter();

		SearchParameter search = new SearchParameter();
		SearchManager.searchDevice(search, client, null);
        
        pd = ProgressDialog.show(this, "Wait please", "Searching for devices...", true, false);
        
        mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i=0; i<SearchClient.listDevices.size(); i++){
        			if(!listItems.contains(SearchClient.listDevices.get(i).getFriendlyName(LocalizedString.LANGUAGE_EN)))
        				listItems.add(SearchClient.listDevices.get(i).getFriendlyName(LocalizedString.LANGUAGE_EN));
        		}
                
                adapter.notifyDataSetChanged();
                pd.dismiss();
            }}, 3000);
    	
    }
    
    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    /*public void searchAgain(View v) {
    	
    	SearchClient.listDevices.clear(); 
    	listItems.clear();
    	adapter.notifyDataSetChanged();
    	
		client = new SearchClient();
        search = new SearchParameter();
		
        search.setServiceTypes(new QNameSet(new QName("BasicServices", namespace)));
		SearchManager.searchDevice(search, client, null);
		//SearchManager.searchService (search, client);
		
        pd = ProgressDialog.show(this, "Wait please", "Searching for devices...", true, false);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i=0; i<SearchClient.listDevices.size(); i++){
        			if(!listItems.contains(SearchClient.listDevices.get(i).getFriendlyName(LocalizedString.LANGUAGE_EN)))
        				listItems.add(SearchClient.listDevices.get(i).getFriendlyName(LocalizedString.LANGUAGE_EN));
        		}
                
                adapter.notifyDataSetChanged();
                pd.dismiss();
            }}, 2500);
			
        adapter.notifyDataSetChanged();
    }*/
    
}


