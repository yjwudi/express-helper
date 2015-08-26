package com.lyj.user;

//import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lyj.dianzifuwu.MainActivity;
import com.lyj.dianzifuwu.R;

@SuppressLint("NewApi")
public class requestActivity extends Activity {

	private Button sendRequestBtn;
	private EditText nameText;
	private EditText expressText;
	private EditText destText;
	private EditText sourceText;
	private EditText payText;
	private EditText deadlineText;
	private EditText telText;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
		.detectDiskReads()  
		.detectDiskWrites()  
		.detectNetwork()  
		.penaltyLog()  
		.build());

		sendRequestBtn = (Button) findViewById(R.id.button1);
		nameText = (EditText) findViewById(R.id.editText_name);
		expressText = (EditText) findViewById(R.id.editText_express);
		destText = (EditText) findViewById(R.id.editText_destination);
		sourceText = (EditText) findViewById(R.id.editText_source);
		payText = (EditText) findViewById(R.id.editText_pay);
		deadlineText = (EditText) findViewById(R.id.editText_time);
		telText = (EditText) findViewById(R.id.editText_tel);

		sendRequestBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				String userName = nameText.getText().toString();
				String express = expressText.getText().toString();
				String destination = destText.getText().toString();
				String source = sourceText.getText().toString();
				String pay = payText.getText().toString();
				String deadline = deadlineText.getText().toString();
				String telephone = telText.getText().toString();

				boolean response = false;
				try {
					response = sendRequest(userName, telephone, express,
							destination, source, pay, deadline);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			    System.out.println(response);
				 if(response){
					 Toast.makeText(requestActivity.this, "Release Successfully", Toast.LENGTH_LONG).show();
					 requestActivity.this.finish();
				 }

			}
		});

	}

	public Boolean sendRequest(String userName, String tel, String express,
			String destination, String source, String pay, String deadline) throws ParseException {
		String response = "false";
		String url = "http://10.0.2.2:8080/Test/services/OperationDAO";
		// String userName, String telephone, String express, String to, String
		// from, String pay,Date time
		SoapObject request = new SoapObject("http://test", "UserOperation");

		request.addProperty("userName", userName);
		request.addProperty("telephone", tel);
		request.addProperty("express", express);
		request.addProperty("to", destination);
		request.addProperty("from", source);
		request.addProperty("pay", pay);
		request.addProperty("time", deadline);

		SoapSerializationEnvelope ssoe = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		ssoe.bodyOut = request;
		HttpTransportSE ht = new HttpTransportSE(url);
		try {
			ht.call(null, ssoe);
			SoapObject object = (SoapObject) ssoe.bodyIn;
			response = object.getProperty("return").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Boolean Bl = new Boolean(response);
		return Bl.booleanValue();
	}

}
