package com.dpws.uma.app;

import java.util.ArrayList;

import org.ws4d.java.service.Operation;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.SearchParameter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListOperations extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    public ArrayList<String> listOperations=new ArrayList<String>();

    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapterOp;

    //RECORDING HOW MUCH TIMES BUTTON WAS CLICKED
    int clickCounter=0;
    int serviceNum;
    
	public SearchClient client;
	public SearchParameter search;
	ListView lista;
	
	private Iterator opIterator;
	private Operation op;
	
	public final static String namespace = "http://www.gisum.uma.es/jmeds";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.listoperations);
        adapterOp=new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            listOperations);
        setListAdapter(adapterOp);
        
        lista = (ListView)findViewById(android.R.id.list);
        lista.setClickable(true);
        
        Bundle extras = getIntent().getExtras();
        serviceNum = extras.getInt("item");
        
        opIterator = SearchClient.listServices.get(serviceNum).getOperations(null, null, null, null);
        
       // pd = ProgressDialog.show(this, "Wait please", "Searching for operations...", true, false);
       // mHandler.postDelayed(new Runnable() {
        //    public void run() {
                while (opIterator.hasNext()){
                	op = (Operation)opIterator.next();
                    listOperations.add(op.getName());
                }
                adapterOp.notifyDataSetChanged();
          //      pd.dismiss();
           // }}, 3000);
        
   
       lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        
          @Override
          public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {  
            changeActivity(serviceNum, listOperations.get(position).toString());   	
          }
        });
    }
    
    public void changeActivity(int serviceNum, String operationName){
		Intent i = new Intent(this, InvokeOperation.class);
		i.putExtra("service", serviceNum);
		i.putExtra("operation", operationName);
		startActivity(i);
    	
    }
}
