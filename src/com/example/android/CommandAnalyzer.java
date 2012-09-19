package com.example.android;

import java.util.ArrayList;

public class CommandAnalyzer {
	private static final CommandTokenizer commandTokenizer = new CommandTokenizer();
	
	public static void analyze(ArrayList<String> matches, float[] confidence) {
		commandTokenizer.setInput(matches.iterator().next());
		if (commandTokenizer.getNextToken().equals("позвони")) {
			new PhoneCall(commandTokenizer.getNextToken()).call();
		}
	}
	
}
