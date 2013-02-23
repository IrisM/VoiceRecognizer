package com.example.android;

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ContactBook extends Activity{
	
	private static ContactBook instance;
	private static final int GET_CONTACTS_REQUEST_CODE = 5678; 
	
	private Collection<Contact> contacts;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		contacts.add(new Contact("Ира", "Броварь", "111"));
		Intent viewIntent = new Intent(Intent.ACTION_VIEW);
		viewIntent.setData(Uri.parse("content://contacts/people/1"));
		startActivityForResult(viewIntent, GET_CONTACTS_REQUEST_CODE);		
	}
	
	public static synchronized ContactBook getInstance(Activity parent){
		if (instance == null) {
			//instance = new ContactBook();
			Intent intent = new Intent(parent, ContactBook.class);
			parent.startActivityForResult(intent, GET_CONTACTS_REQUEST_CODE);
		}
		return instance;
	}
	
	public Contact getContact(String firstName, String lastName){
		for (Contact contact : contacts){
			if (firstName.equals(contact.getFirstName()) &&
					lastName.equals(contact.getLastName())){
				return contact;
			}
		}
		return null;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GET_CONTACTS_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			instance = this;
			Log.d("Contact Book", data.getDataString());
		}
	}
}
