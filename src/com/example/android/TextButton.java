package com.example.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

public class TextButton extends Activity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		Log.d("Text Button", "created");
		setContentView(R.layout.main);

		EditText textField = (EditText) findViewById(R.id.text_field);		
		String str = textField.getText().toString();
		//String str = "call 100";
        
		ArrayList<String> matches = new ArrayList<String>();
        matches.add(str);

		Intent intent = new Intent();
		intent.putExtra("matches", matches);
		setResult(RESULT_OK, intent);
		
		Intent commandAnalyzer = new Intent(this, CommandAnalyzer.class);
		commandAnalyzer.putExtra("matches", matches);
		startActivity(commandAnalyzer);	
	
		finish();
	}
}