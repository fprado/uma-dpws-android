package com.dpws.uma.app;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.types.SearchParameter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.dpws.uma.app.device.MyDevice;
import com.dpws.uma.app.device.MyService;

public class ActMain extends Activity {
	private Button _btnStart;
	private Button _btnStop;
	private Button _btnSearch;
	private Button _btnloc;
	//private Button _btnSearchSer;
	public ListView _debug;
	public ArrayAdapter<String> _debug_adapter;
	
	public SearchClient client;
	public SearchParameter search;

	public final static String namespace = "http://www.gisum.uma.es/jmeds";

	public static boolean frameworkRunning = false;
	
	public void debug(String message) {
		_debug_adapter.insert(message, 0);
		Log.d("JMEDS", message);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);

		getViews();
		initViews();

		debug("App started.");
	}

	private void getViews() {
		_btnStart = (Button) findViewById(R.id.btnStart);
		_btnStop = (Button) findViewById(R.id.btnStop);
		_btnSearch = (Button) findViewById(R.id.btnSearch);
		_btnloc = (Button) findViewById(R.id.btnLoc);
		//_btnSearchSer = (Button) findViewById(R.id.btnSearchSer);

		_debug = (ListView) findViewById(R.id.debug);
	}

	private void initViews() {
		_debug_adapter = new ArrayAdapter<String>(this, R.layout.list_item);
		_debug.setAdapter(_debug_adapter);

		_btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				btnStart();
			}
		});
		_btnStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				btnStop();
			}
		});
		_btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				btnSearch("devices");
			}
		});
		_btnloc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loc();
			}
		});
		/*@Override
			public void onClick(View arg0) {
				btnSearch("services");
			}
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.act_main, menu);
		return true;
	}

	public void btnStart() {

		if (!JMEDSFramework.isRunning())
			JMEDSFramework.start(null);
		
		debug("<ANDROID> FRAMEWORK STARTING");

		MyDevice device;
		MyService service;

		try {
			device = new MyDevice();
			service = new MyService();

			debug("Add Service...");
			device.addService(service);

			debug("Start Device...");
			device.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void btnSearch(String parameter) {
		JMEDSFramework.start(null);
		//debug("<ANDROID> FRAMEWORK SEARCHING, please WAIT");

		client = new SearchClient();
		search = new SearchParameter();

		SearchParameter search = new SearchParameter();
		SearchManager.searchDevice(search, client, null);
		
		Intent i = new Intent(this, ListDevices.class);
		startActivity(i);
	}
	
	public void loc() {
		
		Intent i = new Intent(this, ShowLocationActivity.class);
		startActivity(i);
	}
	
	public void btnStop() {
		JMEDSFramework.stop();
		debug("<ANDROID> FRAMEWORK STOPPED.");

	}
	

}