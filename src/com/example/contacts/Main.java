package com.example.contacts;

import java.util.List;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		long timestamp = System.currentTimeMillis();		
		ContactsReader reader = new ContactsReader("FakeContacts.vcf");
//		ContactsReader reader = new ContactsReader("AhmetHascelikFake.vcf");
		List<Contact> contacts = reader.read();
		ContactIndex index = new ContactIndex();
		index.createIndexes(contacts);
		
		long timeTook = System.currentTimeMillis() - timestamp;
		System.out.println("Number of contacts:" + contacts.size());
		System.out.println(index.toString());
		System.out.println("Loaded:" + timeTook + "ms");
		
		
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("- Search Number (s number)");
			System.out.println("- Search Name (d name)");
			System.out.println("- Exit (e)");
			String line = scanner.nextLine();
			if (line.startsWith("e")) {
				System.out.println("bye");
				break;
			}
			else if (line.startsWith("s ")) {
				String number = line.split(" ")[1];
				timestamp = System.currentTimeMillis();
				List<Contact> foundContacts = index.filterNumbers(number);
				timeTook = System.currentTimeMillis() - timestamp;
				for (Contact foundContact : foundContacts) {
					System.out.println(foundContact);
				}
				
				System.out.println("Time elapsed:" + timeTook + "ms");
				System.out.println("Number of records:" + foundContacts.size());
			}
			else if (line.startsWith("d ")) {
				String name = line.split(" ")[1];
				timestamp = System.currentTimeMillis();
				List<Contact> foundContacts = index.filterNames(name);
				timeTook = System.currentTimeMillis() - timestamp;
				for (Contact foundContact : foundContacts) {
					System.out.println(foundContact);
				}
				
				System.out.println("Time elapsed:" + timeTook + "ms");
				System.out.println("Number of records:" + foundContacts.size());
			}
		}
		scanner.close();
	}
	
}
