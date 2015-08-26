package com.lyj.user;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lyj.dianzifuwu.MainActivity;
import com.lyj.dianzifuwu.R;
import com.lyj.manager.managerActivity;
import com.lyj.user.*;

public class registerActivity extends Activity {

	private EditText userNameET;
	private EditText passwordET;
	private EditText repeatPasswordET;
	private EditText phoneNumberET;
	private EditText emailET;
	private Button registerBtn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		userNameET = (EditText) findViewById(R.id.reg_username);
		passwordET = (EditText) findViewById(R.id.passwordEditView);
		repeatPasswordET = (EditText) findViewById(R.id.repeatPasswordEditView);
		phoneNumberET = (EditText) findViewById(R.id.phoneEditView);
		emailET = (EditText) findViewById(R.id.emailEditView);
		registerBtn = (Button) findViewById(R.id.registerBtn);

		//String email = emailET.getText().toString();
		//Toast.makeText(this, "这是一个Toast提示", Toast.LENGTH_LONG).show();
		
		registerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				String userName = userNameET.getText().toString();
				String password = passwordET.getText().toString();
				String repeatPassword = repeatPasswordET.getText().toString();
				String phoneNumber = phoneNumberET.getText().toString();

				Intent intent = new Intent();
				if (password.equals(repeatPassword)&&RegisterUser(userName, password, phoneNumber)) {
					Toast.makeText(registerActivity.this, "Register Successfully", Toast.LENGTH_LONG).show();
					intent = new Intent();
					intent.setClass(registerActivity.this, MainActivity.class);
					startActivity(intent);
					registerActivity.this.finish();
				}

			}
		});

		
	}

	public boolean RegisterUser(String userName, String password, String phoneNumber) {
		String response = "false";
		String url = "10.0.2.2:8080/Test/services/UserDAO";
		SoapObject request = new SoapObject("http://test", "RegisterUser");
		request.addProperty("name", userName);
		request.addProperty("password", password);
		request.addProperty("tel", phoneNumber);

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
