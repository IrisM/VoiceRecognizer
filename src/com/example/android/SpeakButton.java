package com.example.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;

public class SpeakButton extends Activity{

	public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	public static final int SPEAK_BUTTON_REQUEST_CODE = 1111;
	private VoiceRecognizer parentActivity;

	public VoiceRecognizer getParentActivity() {
		return parentActivity;
	}

	public void setParentActivity(VoiceRecognizer parentActivity) {
		this.parentActivity = parentActivity;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		startVoiceRecognitionActivity();
	}

	public void startVoiceRecognitionActivity() {		
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU");
		
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {

			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			Log.d("SpeakButton debug", matches.get(0));
			//float[] confidence = data
			//		.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

			/*
			 * try { FileWriter vars = new FileWriter(name, true); for (String
			 * match : matches){ vars.write(match); vars.append('\t'); }
			 * vars.append('\n'); //vars.write(String.valueOf(confidence[0]));
			 * vars.close(); } catch (IOException e) { e.printStackTrace(); }
			 */

			//int tmp = 0;
			ArrayList<String> conf = new ArrayList<String>();
			for (String ans : matches) {
				conf.add(ans + "\n");// + confidence[tmp]
				//tmp++;
			}
			
			Intent commandAnalyzer = new Intent(this, CommandAnalyzer.class);
			commandAnalyzer.putExtra("matches", matches);
			startActivity(commandAnalyzer);
			
			Intent intent = new Intent();
			intent.putExtra("matches", matches);
			setResult(RESULT_OK, intent);
		}

		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
}
