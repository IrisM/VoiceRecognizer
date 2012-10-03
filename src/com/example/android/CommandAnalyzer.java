package com.example.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CommandAnalyzer extends Activity{
	private static final CommandTokenizer commandTokenizer = new CommandTokenizer();
	public static final int COMMAND_ANALYZER_REQUEST_CODE = 6441;
	private ArrayList<String> matches;
	
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		Log.d("Command Analyzer", "created");
		if (savedInstanceState == null) {
			Log.d("Command Analyzer", "null parent state");
		}
	}
	public void analyze(ArrayList<String> matches, float[] confidence) {
		try{
			commandTokenizer.setInput(matches.iterator().next());
			String command = commandTokenizer.getNextToken(); 
			if (command.equals("позвони")) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.putExtra(Intent.EXTRA_PHONE_NUMBER, commandTokenizer.getNextToken());
				startActivity(intent);
				//new PhoneCall(commandTokenizer.getNextToken()).call();
			} else if (command.equals("смс") || command.equals("sms")){				
				String number = commandTokenizer.getNextToken();
				String message = commandTokenizer.getNextToken();
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.putExtra(Intent.EXTRA_TEXT, message);
				intent.putExtra(Intent.EXTRA_PHONE_NUMBER, number);
				startActivity(intent);
				//new PhoneSMS().send(number, message);
			} else {
				ContactBook.getInstance(this).getContact(command, commandTokenizer.getNextToken());
			}
		} catch (Exception e){
			Log.d("Command Analyzer", "Please repeat",e);
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("Command Analyzer", "result");
		if (requestCode == SpeakButton.SPEAK_BUTTON_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			matches = data.getStringArrayListExtra("matches");
			analyze(matches, null);
			
			Intent intent = new Intent();
			intent.putExtra("matches", matches);
			setResult(RESULT_OK, intent);
			Log.d("Command Analyzer", "successful");			
		}
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
	
}
