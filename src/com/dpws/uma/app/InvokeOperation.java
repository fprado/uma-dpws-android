package com.dpws.uma.app;

import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InvokeOperation extends Activity {
	
	private Button _invokeButton;
	private EditText mEdit;

	private Service service;
	private Operation op;
	private String opName;
	
    public void onCreate(Bundle icicle) {
    	
        super.onCreate(icicle);
        setContentView(R.layout.operation);
        
		getViews();
		initViews();
		
      }
    
	private void getViews() {
        Bundle extras = getIntent().getExtras();
        service = SearchClient.listServices.get(extras.getInt("service"));
        
        _invokeButton = (Button) findViewById(R.id.invoke);
        mEdit   = (EditText)findViewById(R.id.editText);
        
        opName = extras.getString("operation");
    	TextView nameOp = (TextView)findViewById(R.id.textView3);
    	nameOp.setText(opName);   
    	
	}
	
	private void initViews() {

		_invokeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				invoke();
			}
		});
	}
	
	public void invoke(){
		
		try {
			// get Operation
			op = service.getOperation(null, opName, null, null);
					
			// create input value and set string value
			ParameterValue request = op.createInputValue () ;
			ParameterValueManagement.setString(request, "name", mEdit.getText().toString());
			System.err.println ("Invoking " + op.getName() + "  ...");
			
			// invoke operation with prepared input
			ParameterValue result = op.invoke(request, null);
			
			// get string value from answer
			String reply = ParameterValueManagement.getString(result, "reply");
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(reply)
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
			
		} catch (CommunicationException e) {
			e.printStackTrace() ;
		} catch (InvocationException e) {
			e.printStackTrace() ;
		}

	}

}
