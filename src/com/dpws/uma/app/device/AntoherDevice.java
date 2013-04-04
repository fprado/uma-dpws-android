package com.dpws.uma.app.device;

import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.DeviceCommons;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.DataStructure;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.EndpointReference;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.URI;

public class AntoherDevice extends DeviceCommons {

	@Override
	public EndpointReference getEndpointReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMetadataVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator getTransportXAddressInfos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getDiscoveryXAddressInfos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getTransportAndDiscoveryXAddressInfos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getPortTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getServiceReferences(SecurityKey securityKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getServiceReferences(QNameSet servicePortTypes,
			SecurityKey securityKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMatchingServiceReferencesToDataStructure(DataStructure to,
			QNameSet serviceTypes, SecurityKey securityKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceReference getServiceReference(URI serviceId,
			SecurityKey securityKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceReference getServiceReference(EndpointReference serviceEpr,
			SecurityKey securityKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRemote() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator getScopes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeviceReference getDeviceReference(SecurityKey securityKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnectAllServiceReferences(boolean resetServiceRefs) {
		// TODO Auto-generated method stub
		
	}

}
