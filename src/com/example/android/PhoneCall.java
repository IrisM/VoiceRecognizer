package com.example.android;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PhoneCall extends Activity {
	
	private String number;
	
	public PhoneCall(String number) {
		super();
		this.number = number;
	}

	public void setNumber(String number){
		this.number = number;
	}
	
	public String getNumber(){
		return number;
	}

	public void call() {
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + number));
			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e("Dialing example", "Call failed", e);
		}
	}
}
