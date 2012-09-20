package com.example.android;

import java.util.ArrayList;

public class CommandAnalyzer {
	private static final CommandTokenizer commandTokenizer = new CommandTokenizer();
	
	public static void analyze(ArrayList<String> matches, float[] confidence) {
		commandTokenizer.setInput(matches.iterator().next());
		String command = commandTokenizer.getNextToken(); 
		if (command.equals("позвони")) {
			new PhoneCall(commandTokenizer.getNextToken()).call();
		} else if (command.equals("смс") || command.equals("sms")){
			String number = commandTokenizer.getNextToken();
			String message = commandTokenizer.getNextToken();
			new PhoneSMS().send(number, message);
		} else {
			ContactBook.getInstance().getContact(command, commandTokenizer.getNextToken());
		}
	}
	
}
