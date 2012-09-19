package com.example.android;

public class CommandTokenizer {
	
	private String input;
	private String[] tokens;
	private int iter = 0;
	
	public void setInput(String input){
		this.input = input;
		tokens = input.split(" ");
	}
	
	public String getNextToken(){
		return tokens[iter++];
	}
}
