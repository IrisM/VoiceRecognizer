/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class VoiceRecognizer extends Activity  implements OnClickListener{

	private ListView commandsList;

	public static final SimpleDateFormat format = new SimpleDateFormat(
			"dd_MM_yyyy HH_mm_ss");

	// private String name;
	
	public ListView getCommandsList() {
		return commandsList;
	}

	public void setCommandsList(ListView commandsList) {
		this.commandsList = commandsList;
	}

	public void onClick(View v) {
		if (v.getId() == R.id.btn_speak) {
			Intent intent = new Intent(this, SpeakButton.class);
			startActivityForResult(intent, CommandAnalyzer.COMMAND_ANALYZER_REQUEST_CODE);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Voice Recognizer", "created");
		setContentView(R.layout.main);
		Button speakButton = (Button) findViewById(R.id.btn_speak);
		commandsList = (ListView) findViewById(R.id.list);

		// Check to see if a recognition activity is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
			speakButton.setOnClickListener(this);
		} else {
			speakButton.setEnabled(false);
			speakButton.setText("Recognizer not present");
		}

		
		/*
		 * Date date = new Date(); name = format.format(date); name =
		 * "/sdcard/download/" + name + ".txt";
		 */
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("Voice Recognizer", "finished requestCode=" + requestCode + ", resultCode=" + resultCode);
		if (requestCode == CommandAnalyzer.COMMAND_ANALYZER_REQUEST_CODE) {

			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data
					.getStringArrayListExtra("matches");
			Log.d("Voice Recognizer", matches.get(0));
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

			getCommandsList().setAdapter(
					new ArrayAdapter<String>(this,
							android.R.layout.simple_list_item_1, conf));			
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
