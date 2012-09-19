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
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.ListView;

public class VoiceRecognizer extends Activity {

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		Button speakButton = (Button) findViewById(R.id.btn_speak);
		commandsList = (ListView) findViewById(R.id.list);

		// Check to see if a recognition activity is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
			speakButton.setOnClickListener(new SpeakButton(this));
		} else {
			speakButton.setEnabled(false);
			speakButton.setText("Recognizer not present");
		}

		/*
		 * Date date = new Date(); name = format.format(date); name =
		 * "/sdcard/download/" + name + ".txt";
		 */
	}

}
