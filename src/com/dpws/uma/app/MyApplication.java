package com.dpws.uma.app;

import java.io.IOException;

import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.communication.connection.ip.PlatformIPNetworkDetection;
import org.ws4d.java.io.fs.FileSystem;
import org.ws4d.java.platform.io.fs.LocalFileSystem;

import android.app.Application;
import android.util.Log;
//import com.dpws.uma.app.R;

public class MyApplication extends Application {
	private String LOG_TAG = "MyApplication";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		PlatformIPNetworkDetection pind = ((PlatformIPNetworkDetection) IPNetworkDetection.getInstance());
		pind.initContext(getApplicationContext());
		try {
			Log.d(LOG_TAG, "Set Android Context for FileSystem...");
			LocalFileSystem lfs = ((LocalFileSystem) FileSystem.getInstance());
			lfs.setAndroidContext(getApplicationContext());
		} catch (IOException e) {
			Log.w(LOG_TAG, "Error setting Context for FileSystem.");
			e.printStackTrace();
		}
	}
	
}
