package com.dpws.uma.app.device;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ws4d.java.communication.connection.ip.IPNetworkDetection;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.ScopeSet;

import android.annotation.SuppressLint;

public class MyDevice extends DefaultDevice {
	String[] scopes;
	ScopeSet scopeList = null;
	QNameSet qnsDeviceTypes = null;
	
	public final static String namespace = "http://www.gisum.uma.es/jmeds";

	public MyDevice() {
		super();
		printInfo();
		
		scopeList = new ScopeSet(new String[] { "urn:"+android.os.Build.PRODUCT });

		qnsDeviceTypes = new QNameSet(new QName(android.os.Build.DEVICE,
				"http://www.gisum.uma.es/jmeds"));
		
		addFriendlyName("en-GB", android.os.Build.PRODUCT);
		addManufacturer("en-GB", android.os.Build.MANUFACTURER);
		addModelName("en-GB", android.os.Build.MODEL);

		setFirmwareVersion("v1.0");
		//setManufacturerUrl("http://www.manu-url.de");
		setModelNumber(android.os.Build.MODEL);
		//setModelUrl("https://model-homepage.de");
		//setSerialNumber(android.os.Build.SERIAL);
		//setPresentationUrl("http://www.presentation.de/MyDevice");

		setScopes(scopeList);
		setPortTypes(new QNameSet(new QName(android.os.Build.MODEL, namespace)));
		
		Iterator itr = IPNetworkDetection.getInstance().getNetworkInterfaces();
		
		while(itr.hasNext()){
			System.out.println(itr.next().toString());
		}
		
		//NetworkInterface iface = IPNetworkDetection.getInstance().getNetworkInterface("wlan0");
		//IPDiscoveryDomain domain = IPNetworkDetection.getInstance().getIPDiscoveryDomainForInterface(iface, false);
		//this.addBinding(new IPDiscoveryBinding(DPWSCommunicationManager.COMMUNICATION_MANAGER_ID, domain));
		
	
	}
	
	/**
	 * Returns the current time in format HH:MM:SS.mmm.
	 * 
	 * @return The current time in format HH:MM:SS.mmm.
	 */
	@SuppressLint("SimpleDateFormat")
	static String getCurrentTime(String format) throws IllegalArgumentException {
		if (format == null || format.length() == 0) format = "HH:mm:ss:SSS";
		SimpleDateFormat dateformat = new SimpleDateFormat(format);

		return dateformat.format(new Date());
	};
	
	private void printInfo(){
		System.err.println("Device info:");
		System.err.println("Device: "  + android.os.Build.DEVICE);
		System.err.println("Manufacturer: " + android.os.Build.MANUFACTURER);
		System.err.println("Model: " + android.os.Build.MODEL);
		System.err.println("Product: " + android.os.Build.PRODUCT);
		System.err.println(getEndpointReference());

	}
}
