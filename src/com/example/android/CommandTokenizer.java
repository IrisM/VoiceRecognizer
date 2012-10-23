package com.example.android;

import android.util.Log;

public class CommandTokenizer {
	
	//private String input;
	private String[] tokens;
	private int iter = 0;
	
	public void setInput(String input){
		//this.input = input;		
		iter = 0;
		tokens = input.split(" ");
		Log.d("Command Tokenizer", input + " " + tokens.length);
	}
	
	public String getNextToken(){
		Log.d("Command Tokenizer", "gNT " + tokens.length + " " + iter);
		if (tokens.length == iter) 
			throw new IndexOutOfBoundsException("No more tokens");
		return tokens[iter++];
	}
	
	public boolean hasNextToken(){
		Log.d("Command Tokenizer", "hNT " + tokens.length + " " + iter);
		return !(tokens.length == iter);
	}
}
