package com.example.contacts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ContactsReader {

	public static final String BEGIN_VCARD = "BEGIN:VCARD";
	public static final String END_VCARD = "END:VCARD";
	public static final String FULLNAME = "FN";
	public static final String PHONE = "TEL";

	private String path;

	public ContactsReader(String path) {
		super();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public List<Contact> read() {
		List<Contact> contacts = new LinkedList<Contact>();

		BufferedReader vcf = null;
		try {
			vcf = new BufferedReader(new FileReader(path));
			String line = null;
			Contact newContact = null;
			while ((line = vcf.readLine()) != null) {
				if (line.equals(BEGIN_VCARD)) {
					newContact = new Contact();
				} else if (line.equals(END_VCARD)) {
					if (newContact.isValid()) {
						contacts.add(newContact);
					}
					newContact = null;
				} else if (line.startsWith(FULLNAME)) {
					String fullname = line.split(":")[1];
					newContact.setFullname(fullname);
				} else if (line.startsWith(PHONE)) {
					String number = line.split(":")[1];
					newContact.addNumber(number);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (vcf != null) {
				try {
					vcf.close();
				} catch (IOException e) {
				}
			}
		}

		Collections.sort(contacts, new Comparator<Contact>() {
			@Override
			public int compare(Contact o1, Contact o2) {
				return o1.getFullname().toUpperCase().compareTo(o2.getFullname().toUpperCase());
			}
		});

		return contacts;
	}
}
