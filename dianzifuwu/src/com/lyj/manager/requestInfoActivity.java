package com.lyj.manager;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
import android.widget.TextView;

import com.lyj.dianzifuwu.MainActivity;
import com.lyj.dianzifuwu.R;
import com.lyj.user.registerActivity;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class requestInfoActivity extends Activity{
	
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_info);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
		.detectDiskReads()  
		.detectDiskWrites()  
		.detectNetwork()  
		.penaltyLog()  
		.build());
		
		
		
	}
	
	
	
	
	
	
	
	
}