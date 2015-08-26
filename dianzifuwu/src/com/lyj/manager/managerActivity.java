package com.lyj.manager;

import java.util.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lyj.dianzifuwu.MainActivity;
import com.lyj.dianzifuwu.R;
import com.lyj.user.registerActivity;
import com.lyj.user.requestActivity;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class managerActivity extends ListActivity {
	
	private List<Map<String, Object>> mData;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		
		mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);

	}
	
	public final class ViewHolder{
        public TextView name;
        public TextView pay;
        public TextView dest;
        public TextView source;
        public TextView tel;
        public TextView deadline;
        public Button releaseBtn;
        public Button deleteBtn;
    }
	
	public class MyAdapter extends BaseAdapter{
		 
        private LayoutInflater mInflater;
         
         
        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }
 
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }
 
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             
            ViewHolder holder = null;
            if (convertView == null) {
                 
                holder=new ViewHolder();  
                 
                convertView = mInflater.inflate(R.layout.activity_manager, null);
   
                holder.name = (TextView)convertView.findViewById(R.id.nameInfo);
                holder.pay = (TextView)convertView.findViewById(R.id.payInfo);
                holder.source = (TextView)convertView.findViewById(R.id.sourceInfo);
                holder.dest = (TextView)convertView.findViewById(R.id.destInfo);
                holder.tel = (TextView)convertView.findViewById(R.id.telInfo);
                holder.deadline = (TextView)convertView.findViewById(R.id.deadlineInfo);
                holder.releaseBtn = (Button)convertView.findViewById(R.id.releaseBtn);
                holder.deleteBtn = (Button)convertView.findViewById(R.id.delBtn);
                convertView.setTag(holder);
                 
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
             
             
            holder.name.setText((String)mData.get(position).get("UserName"));
            holder.pay.setText((String)mData.get(position).get("Pay"));
            holder.source.setText((String)mData.get(position).get("Source"));
            holder.dest.setText((String)mData.get(position).get("Destination"));
            holder.deadline.setText((String)mData.get(position).get("Deadline"));
            holder.tel.setText((String)mData.get(position).get("UserTel"));
            int len = ((String) mData.get(position).get("UserName")).length();
            final String userName = ((String)mData.get(position).get("UserName")).substring(10,len);
            
            holder.releaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean response = managerRelease(userName);
                    if(response){
                    	Toast.makeText(managerActivity.this, "Release Successfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
             
             
            return convertView;
        }
         
    }
	
	public boolean managerRelease(String userName){
		String response = "false";
		String url = "http://10.0.2.2:8080/Test/services/OperationDAO";

		SoapObject request = new SoapObject("http://test", "ManagerRelease");
		request.addProperty("userName", userName);
System.out.println("uu: " + userName);

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


	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		String requests = getRequest();
		//System.out.println(requests);

		StringTokenizer rTokenizer = new StringTokenizer(requests, "&");
		while (rTokenizer.hasMoreTokens()) {
			map = new HashMap<String, Object>();
			String expressInfo = rTokenizer.nextToken();
			System.out.println(expressInfo);
			String[] infoArray = new String[10];
			int i = 0, j = 0, first = 0, second = 0;
			for (i = 0; i < expressInfo.length(); i++) {
				if (expressInfo.charAt(i) == '|') {
					second = i;
					infoArray[j++] = expressInfo.substring(first, second);
					first = second + 1;
				}
			}
			infoArray[j++] = expressInfo.substring(first, expressInfo.length());
			System.out.println(j-1);
		System.out.println("di5: " + infoArray[j-1]);
			map.put("UserName", "UserName: " + infoArray[0]);
			map.put("Pay", "Pay: " + infoArray[4]);
			map.put("Destination", "Destination: " + infoArray[2]);
			map.put("Source", "Source: " + infoArray[3]);
			map.put("UserTel", "UserTel: " + infoArray[1]);
			map.put("Deadline", "Deadline: " + infoArray[5]);
			list.add(map);
		}

		return list;
	}
	
	 protected void onListItemClick(ListView l, View v, int position, long id) {
         
	        Log.v("managerListView-click", (String)mData.get(position).get("UserName"));
	    }

	public String getRequest() {
		String response = new String();
		String url = "http://10.0.2.2:8080/Test/services/OperationDAO";
		SoapObject request = new SoapObject("http://test", "ManagerOperation");

		SoapSerializationEnvelope ssoe = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		ssoe.bodyOut = request;
		HttpTransportSE ht = new HttpTransportSE(url);
		try {
			ht.call(null, ssoe);
			SoapObject object = (SoapObject) ssoe.bodyIn;
			response = object.getProperty("return").toString();
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}