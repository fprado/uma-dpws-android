package com.dpws.uma.app.device;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.constants.SOAPConstants;
import org.ws4d.java.schema.ComplexType;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.schema.Type;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.service.Fault;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.parameter.StringValue;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.URI;

public class MyService extends DefaultService {
	private String namespace = "http://www.gisum.uma.es/jmeds";

	private URI serviceId = new URI(namespace + "/myService");
	private QName serviceType = new QName("MyService", namespace);

	Fault fault;

	/**
	 * The standard constructor.
	 */
	public MyService() {
		super();

		setServiceId(serviceId);

		// define faults...
		fault = new Fault("InvalidInput");
		fault.setElement(new Element("Description", namespace,
				SchemaUtil.TYPE_STRING));

		// add operations...
		addOperation(new GetCurrentTime());
		addOperation(new CalcAddition());
	}

	/**
	 * Gets the current time.
	 */
	private class GetCurrentTime extends Operation {

		GetCurrentTime() {
			super("Get Current Time", serviceType);

			Type stringType = SchemaUtil.TYPE_STRING;
			setOutput(new Element(new QName("TimeOutput", namespace),
					stringType));
			setInputName("TimeInput");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.ws4d.java.service.Operation#invoke(org.ws4d.java.service.
		 * ParameterValue)
		 */
		public ParameterValue invokeImpl(ParameterValue parameterValue,
				CredentialInfo credentialInfo) throws InvocationException,
				CommunicationException {
			ParameterValue paramTimeVal = createOutputValue();
			ParameterValueManagement.setString(paramTimeVal, null,
					MyDevice.getCurrentTime(null));
			return paramTimeVal;
		}
	}

	/**
	 * Simple Calculator.
	 */
	private class CalcAddition extends Operation {
		final String INPUT = "Input";
		final String CTYPE = "Variables";
		final String VAL_A = "Value_a";
		final String VAL_B = "Value_b";

		CalcAddition() {
			super("Calculate Addition", serviceType);
			
			Element inputElement = new Element(new QName(INPUT, namespace));
			ComplexType complexInput = new ComplexType(new QName(CTYPE, namespace), ComplexType.CONTAINER_SEQUENCE);
			complexInput.addElement(new Element(new QName(VAL_A, namespace), SchemaUtil.TYPE_STRING));
			complexInput.addElement(new Element(new QName(VAL_B, namespace), SchemaUtil.TYPE_STRING));
			inputElement.setType(complexInput);
			setInput(inputElement);
			
			setOutput(new Element(new QName("Result", namespace), SchemaUtil.TYPE_STRING));
			addFault(fault);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seeorg.ws4d.java.service.Operation#invoke(org.ws4d.java.service.
		 * ParameterValue)
		 */
		public ParameterValue invokeImpl(ParameterValue parameterValue,
				CredentialInfo credentialInfo) throws InvocationException,
				CommunicationException {

			ParameterValue paramTimeVal = createOutputValue();

			try {
				long val_a = Long.parseLong(((StringValue) parameterValue.get(VAL_A)).get());
				long val_b = Long.parseLong(((StringValue) parameterValue.get(VAL_B)).get());

				ParameterValueManagement.setString(paramTimeVal, null,
						String.valueOf(val_a + val_b));
			} catch (NumberFormatException e) {
				ParameterValueManagement.setString(paramTimeVal, null,
						"<ERROR>");

				ParameterValue result = fault.createValue();
				ParameterValueManagement.setString(result, null,
						"Invalid Input: Integer values expected.");
				throw new InvocationException(fault,
						SOAPConstants.SOAP_FAULT_SENDER, result);
			}

			return paramTimeVal;
		}

	}
}
