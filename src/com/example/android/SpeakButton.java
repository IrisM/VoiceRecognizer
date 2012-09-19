package com.example.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

public class SpeakButton extends Activity implements OnClickListener {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private VoiceRecognizer parentActivity;

	public SpeakButton(VoiceRecognizer parentActivity) {
		super();
		this.parentActivity = parentActivity;
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btn_speak) {
			startVoiceRecognitionActivity();
		}
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
		if (requestCode == SpeakButton.VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {

			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			float[] confidence = data
					.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

			/*
			 * try { FileWriter vars = new FileWriter(name, true); for (String
			 * match : matches){ vars.write(match); vars.append('\t'); }
			 * vars.append('\n'); //vars.write(String.valueOf(confidence[0]));
			 * vars.close(); } catch (IOException e) { e.printStackTrace(); }
			 */

			int tmp = 0;
			ArrayList<String> conf = new ArrayList<String>();
			for (String ans : matches) {
				conf.add(ans + "\n" + confidence[tmp]);
				tmp++;
			}

			parentActivity.getCommandsList().setAdapter(
					new ArrayAdapter<String>(this,
							android.R.layout.simple_list_item_1, conf));
			CommandAnalyzer.analyze(matches, confidence);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
