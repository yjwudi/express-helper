package com.lyj.user;

import com.lyj.dianzifuwu.MainActivity;
import com.lyj.dianzifuwu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class userActivity extends Activity{
	
	private Button requestBtn;
	private Button serveBtn;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		requestBtn = (Button)findViewById(R.id.requestBtn);
		serveBtn = (Button)findViewById(R.id.serviceBtn);
		
		//requestBtn.setBackgroundColor(0x4876FF);
		//serveBtn.setBackgroundColor(0x4876FF);
		
		requestBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 新建一个Intent
				Intent intent = new Intent();
				intent.setClass(userActivity.this, requestActivity.class);
				startActivity(intent);
			}
		});
		
		serveBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 新建一个Intent
				Intent intent = new Intent();
				intent.setClass(userActivity.this, serveActivity.class);
				startActivity(intent);
			}
		});
	}
}





