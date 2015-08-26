package com.lyj.dianzifuwu;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lyj.user.*;
import com.lyj.manager.*;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private EditText accountText;
	private EditText passwordText;
	private Button loginButton;
	private Button registerButton;
	private String account;
	private String password;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		accountText = (EditText) findViewById(R.id.accountText);
		passwordText = (EditText) findViewById(R.id.passwordText);
		loginButton = (Button) findViewById(R.id.loginButton);
		registerButton = (Button) findViewById(R.id.registerButton);

		// final String userName = accountText.getText().toString();
		// final String userPassword = passwordText.getText().toString();

		loginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				String userName = accountText.getText().toString();
				String userPassword = passwordText.getText().toString();
				// 新建一个Intent
				Intent intent = new Intent();

				if (callSignIn(userName, userPassword, "Login")) {
					if (userName.equals("JoinWei")) {
						intent.setClass(MainActivity.this,
								managerActivity.class);
					} else {
						intent.setClass(MainActivity.this, userActivity.class);
					}
					startActivity(intent);
					MainActivity.this.finish();
				}

			}
		});
		registerButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 新建一个Intent
				Intent intent = new Intent();
				// 制定intent要启动的类
				intent.setClass(MainActivity.this, registerActivity.class);
				// 启动一个新的Activity
				startActivity(intent);

			}
		});

	}

	@SuppressLint("UseValueOf")
	public boolean callSignIn(String userName, String userPassword,
			String funcName) {
		String response = "false";
		String url = "http://10.0.2.2:8080/Test/services/UserDAO";

		SoapObject request = new SoapObject("http://test", funcName);
		request.addProperty("name", userName);
		request.addProperty("password", userPassword);

		SoapSerializationEnvelope ssoe = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		ssoe.bodyOut = request;
		HttpTransportSE ht = new HttpTransportSE(url);
		try {
			ht.call(null, ssoe);
			SoapObject object = (SoapObject) ssoe.bodyIn;
			response = object.getProperty("return").toString();
			// System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Boolean Bl = new Boolean(response);
		return Bl.booleanValue();
	}

}
