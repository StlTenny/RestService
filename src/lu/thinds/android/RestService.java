package lu.thinds.android;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;

public class RestService{
	
	private ArrayList <ParcelableNameValuePair> params;
	private ArrayList <ParcelableNameValuePair> headers;
	
	
	private String url;
	private final Handler mHandler;
	private Context mContext;
	
	public final static int GET = 1;
	public final static int POST = 2;
	
	public RestService(Handler mHandler, Context mContext, String url){
		this.mHandler = mHandler;
		this.mContext = mContext;
		this.url = url;
		params = new ArrayList<ParcelableNameValuePair>();
		headers = new ArrayList<ParcelableNameValuePair>();
	}
	
	public void addParam(String name, String value){
		params.add(new ParcelableNameValuePair(name, value));
	}
	
	public void addHeader(String name, String value){
		headers.add(new ParcelableNameValuePair(name,value));
	}
	
	
	public void execute(int method) {
	     ResultReceiver receiver;
	     receiver = new ResultReceiver(mHandler){
    	    @Override
    	    protected void onReceiveResult(int resultCode, Bundle resultData) {
    	    	mHandler.obtainMessage(0,0,0,resultData.getString("result")).sendToTarget();
    	    }
	     };
	     final Intent intent = new Intent(mContext, ExecuteRequest.class);
	     intent.putParcelableArrayListExtra("headers", (ArrayList<? extends Parcelable>) headers);
	     intent.putExtra("params", params);
	     intent.putExtra("url", url);
	     intent.putExtra("receiver", receiver);
	     intent.putExtra("method", method);
	     mContext.startService(intent);
	}
}