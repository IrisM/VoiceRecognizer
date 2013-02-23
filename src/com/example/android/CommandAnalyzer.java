package com.example.android;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class CommandAnalyzer extends Activity{
	private static final CommandTokenizer commandTokenizer = new CommandTokenizer();
	public static final int COMMAND_ANALYZER_REQUEST_CODE = 6441;
	public static final int SUB_ACTIVITY_REQUEST_CODE = 2309;
	public static final int SUB_ACTIVITY_CALL_REQUEST_CODE = 2310;
	public static final int SUB_ACTIVITY_SMS_REQUEST_CODE = 2311;
	public static final int SUB_ACTIVITY_CONTACTS_REQUEST_CODE = 2312;
	private ArrayList<String> matches;
    private SmsManager manager;
	
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		Log.d("Command Analyzer", "created");
		Intent intent = getIntent();
		matches = intent.getStringArrayListExtra("matches");
		Log.d("Command Analyzer", "matches.size = " + matches.size());
		analyze();
	} 	
	public void analyze() {
		try{
			Iterator<String> iter = matches.iterator();
			if (iter.hasNext()){
				String firstVariant = iter.next();
				commandTokenizer.setInput(firstVariant);				
				Log.d("Command Analyzer", "first variant: " + firstVariant);
				if (commandTokenizer.hasNextToken()){
					String command = commandTokenizer.getNextToken(); 
					if (command.equals("позвони") || command.equals("call")) {
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + commandTokenizer.getNextToken()));
						//intent.setData();
						//intent.putExtra(Intent.EXTRA_PHONE_NUMBER, "tel:" + commandTokenizer.getNextToken());
						startActivityForResult(intent, SUB_ACTIVITY_CALL_REQUEST_CODE);
						//new PhoneCall(commandTokenizer.getNextToken()).call();
					} else if (command.equals("смс") || command.equals("sms")){				
						String number = commandTokenizer.getNextToken();
						String message = commandTokenizer.getNextToken();
//						Intent intent = new Intent(Intent.ACTION_SENDTO);
//						intent.putExtra(Intent.EXTRA_TEXT, message);
//						intent.putExtra(Intent.EXTRA_PHONE_NUMBER, number);
//						startActivityForResult(intent, SUB_ACTIVITY_SMS_REQUEST_CODE);
						//new PhoneSMS().send(number, message);

		                manager = SmsManager.getDefault();
						manager.sendTextMessage(number, null, message, null, null);
		                Toast.makeText(this,"SMS sent", Toast.LENGTH_LONG).show();
		                
		                Intent intent = new Intent();
		    			intent.putExtra("matches", matches);
		    			setResult(RESULT_OK, intent);
		    			
		    			finish();
					} else {
						Intent viewIntent = new Intent(Intent.ACTION_VIEW);
						viewIntent.setData(Uri.parse("content://contacts"));
						viewIntent.setType(Phone.CONTENT_TYPE);
						startActivityForResult(viewIntent, SUB_ACTIVITY_CONTACTS_REQUEST_CODE);
						//ContactBook.getInstance(this).getContact(command, commandTokenizer.getNextToken());
					}
				} else {
					Log.d("Command Analyzer", "No tokens");
				}
			} else {
				Log.d("Command Analyzer", "No commands");
			}
		} catch (Exception e){
			Log.d("Command Analyzer", "Please repeat",e);
            Toast.makeText(this, "error: " + e.toString(), Toast.LENGTH_LONG).show();
            finish();
		}
	}
		
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("Command Analyzer", "finished requestCode=" + requestCode + ", resultCode=" + resultCode);
		/*if (requestCode == SpeakButton.SPEAK_BUTTON_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			matches = data.getStringArrayListExtra("matches");
			analyze(matches, null);
			
			Intent intent = new Intent();
			intent.putExtra("matches", matches);
			setResult(RESULT_OK, intent);
			Log.d("Command Analyzer", "successful");			
		}*/
		if (resultCode == RESULT_OK) {
			switch (requestCode){
			case SUB_ACTIVITY_CALL_REQUEST_CODE:
				break;
			case SUB_ACTIVITY_SMS_REQUEST_CODE:
				break;
			case SUB_ACTIVITY_CONTACTS_REQUEST_CODE:
				Log.d("Contact Book", data.getDataString());
				break;
			default:
				Log.e("Command Analyzer", "Unknown requestCode = " + requestCode);
			}
			Intent intent = new Intent();
			intent.putExtra("matches", matches);
			setResult(RESULT_OK, intent);
		} else {
			Intent intent = new Intent();
			intent.putExtra("matches", matches);
			setResult(RESULT_CANCELED, intent);	
		}		
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
	
}
