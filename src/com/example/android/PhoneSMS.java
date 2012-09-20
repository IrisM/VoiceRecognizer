package com.example.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class PhoneSMS extends Activity{
	
	public void send(String number, String message){
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
		sendIntent.setData(Uri.parse("tel:" + number + "; data:text/plain," + message));
		startActivity(sendIntent);
	}
}
