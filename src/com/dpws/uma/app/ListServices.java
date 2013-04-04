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


public class ListServices extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listSrv=new ArrayList<String>();

    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapterSrv;

    //RECORDING HOW MUCH TIMES BUTTON WAS CLICKED
    int clickCounter=0;
    int position;
    String serviceID, serviceName;
    int posBar;
    
	public SearchClient client;
	public SearchParameter search;
	ListView listaSrv;
	
	private ProgressDialog pd = null;
	private Handler mHandler = new Handler();
	
	public final static String namespace = "http://www.gisum.uma.es/jmeds";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.listservices);
        adapterSrv=new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            listSrv);
        setListAdapter(adapterSrv);
        
        Bundle extras = getIntent().getExtras();
        position = extras.getInt("item");
               
        listaSrv = (ListView)findViewById(android.R.id.list);
        listaSrv.setClickable(true);
        
        pd = ProgressDialog.show(this, "Wait please", "Searching for services...", true, false);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i=0; i<SearchClient.listServices.size(); i++){
                	if(!listSrv.contains(SearchClient.listServices.get(i).toString()))
                		serviceID = SearchClient.listServices.get(i).getServiceId().toString();
                		serviceName = serviceID.substring(serviceID.lastIndexOf("/")+1);
                		listSrv.add(serviceName);
        		}
                
                adapterSrv.notifyDataSetChanged();
                pd.dismiss();
            }}, 2500);
        
        listaSrv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        	  
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
		Intent i = new Intent(this, ListOperations.class);
		i.putExtra("item", position);
		startActivity(i);
    	
    }
    
    public void search(){
    	listSrv.clear();
    	SearchClient.listServices.clear();  
    	
      	client = new SearchClient();
        search = new SearchParameter();
      	
        search.setDeviceTypes (new QNameSet ( new QName (
          		SearchClient.listDevices.get(position).getFriendlyName(
          				LocalizedString.LANGUAGE_EN) , namespace)));
        
        search.setServiceTypes(new QNameSet(ListDevices.namesSrv));     
          
        //SearchManager.searchDevice(search, client, null);
  		SearchManager.searchService (search, client);
        
        pd = ProgressDialog.show(this, "Wait please", "Searching for services...", true, false);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i=0; i<SearchClient.listServices.size(); i++){
                	if(!listSrv.contains(SearchClient.listServices.get(i).toString()))
                		serviceID = SearchClient.listServices.get(i).getServiceId().toString();
                		serviceName = serviceID.substring(serviceID.lastIndexOf("/")+1);
                		listSrv.add(serviceName);
        		}
                
                adapterSrv.notifyDataSetChanged();
                pd.dismiss();
            }}, 2500);
    	
    }
        
    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    /*public void searchAgain(View v) {

		for (int i=0; i<SearchClient.listServices.size(); i++){
			if(!listSrv.contains(SearchClient.listServices.get(i).toString()))
				listSrv.add(SearchClient.listServices.get(i).getServiceId().toString());
			}						
		
        adapterSrv.notifyDataSetChanged();
    }*/
    
}
