package com.dpws.uma.app;

import java.util.ArrayList;

import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.types.LocalizedString;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.SearchParameter;

public class SearchClient extends DefaultClient {

	public final static String namespace = "http://www.gisum.uma.es/jmeds";
	final static QName service = new QName("BasicServices", namespace);

	public static ArrayList<Device> listDevices = new ArrayList<Device>();
	public static ArrayList<Service> listServices = new ArrayList<Service>();
	
	public static boolean endSearch = false;

	public void serviceFound(ServiceReference servRef, SearchParameter search) {

		try {

			{
				if (!listServices.contains(servRef.getService()))
					listServices.add(servRef.getService());

				// get operation
				/*
				 * Operation op = servRef.getService().getOperation(service,
				 * "CameraRecordOperation", null, null);
				 * 
				 * // create input value and set string values ParameterValue
				 * request = op.createInputValue();
				 * ParameterValueManagement.setString(request, "name", "Bobby");
				 * //ParameterValueManagement.setString(request, "address[0]",
				 * "2500 University Blvd.");
				 * //ParameterValueManagement.setString(request, "address[1]",
				 * "263 Laurent");
				 * 
				 * System.err.println("Invoking HelloWorldOp...");
				 * 
				 * // invoke operation with prepared input ParameterValue result
				 * = op.invoke(request, CredentialInfo.EMPTY_CREDENTIAL_INFO);
				 * 
				 * System.err.println("Finished invoking HelloWorldOp..."); //
				 * get string value from answer String reply =
				 * ParameterValueManagement.getString(result, "reply");
				 * 
				 * System.out.println(reply);
				 */
			}

		} catch (CommunicationException e) {
			e.printStackTrace();
		} /*
		 * catch (InvocationException e) { e.printStackTrace(); }
		 */

	}

	public void deviceFound(DeviceReference devRef, SearchParameter search) {
		try {
			Device device = devRef.getDevice();

			if (!listDevices.contains(device))
				listDevices.add(device);

		} catch (CommunicationException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void finishedSearching(boolean entityFound, SearchParameter search) {
		//printDevices();
		//printServices();
		endSearch = true;
	}

	public void printDevices() {
		System.out.println("Devices found: ");
		for (int i = 0; i < listDevices.size(); i++) {
			System.err.println(listDevices.get(i).getFriendlyName(
					LocalizedString.LANGUAGE_EN));
		}
	}

	public void printServices() {
		System.out.println("Services found: ");
		for (int i = 0; i < listServices.size(); i++) {
			System.out.println(listServices.get(i).getDescriptions());
		}

	}

}
