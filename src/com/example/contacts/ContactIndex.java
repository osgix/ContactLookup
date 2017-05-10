package com.example.contacts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class ContactIndex {

	public static final int MIN_NUMBER_LENGTH = 3;
	
	private TreeMap<String, Set<Integer>> numberIndex;
	private TreeMap<String, Set<Integer>> t9Index;
	private TreeMap<String, Set<Integer>> nameIndex;
	private Contact[] contacts;
	
	public ContactIndex() {
		numberIndex = new TreeMap<>();
		t9Index = new TreeMap<>();
		nameIndex = new TreeMap<>();
	}
	
	public void createIndexes(List<Contact> contactList) {
		this.contacts = new Contact[contactList.size()];
		
		int i = 0; 
		for (Contact contact : contactList) {
			this.contacts[i] = contact;
			
			String fullname = contact.getFullname();
			List<String> names = getNames(fullname);
			for (String name : names) {
				Set<String> variations = getNameVariations(name);
				addVariations(i, variations, nameIndex);
			}
			
			List<String> t9Equavalents = getT9Equavalents(fullname);
			for (String t9Equavalent : t9Equavalents) {
				Set<String> variations = getT9Variations(t9Equavalent);
				addVariations(i, variations, t9Index);
			}
			
			List<String> numbers = contact.getNumbers();
			for (String number : numbers) {
				Set<String> variations = getNumberVariations(number);
				addVariations(i, variations, numberIndex);
			}
			i++;
		}
	}

	private void addVariations(int i, Set<String> variations, TreeMap<String, Set<Integer>> index) {
		for (String variation : variations) {
			if (index.containsKey(variation)) {
				Set<Integer> keys = index.get(variation);
				keys.add(i);
			}
			else {
				Set<Integer> keys = new TreeSet<Integer>();
				keys.add(i);
				index.put(variation, keys);
			}
		}
	}

	private List<String> getNames(String fullname) {
		fullname = fullname.toLowerCase();
		fullname = fullname.replaceAll("[^abc\u00e7defg\u011fh\u0131ijklmno\u00f6pqrs\u015ftu\u00fcwxvyz]", " ");
		String[] names = fullname.split(" ");
		return Arrays.asList(names);
	}

	private Set<String> getNameVariations(String name) {
		Set<String> variations = new HashSet<>();
		int endIndex = 1;
		int length = name.length();
		while (endIndex <= length) {
			String variation = name.substring(0, endIndex);
			variations.add(variation);
			endIndex++;
		}
		
		return variations;
	}	
	
	private Set<String> getT9Variations(String t9Equavalent) {
		Set<String> variations = new HashSet<>();
		int endIndex = 1;
		int length = t9Equavalent.length();
		
		while(endIndex <= length) {
			String variation = t9Equavalent.substring(0, endIndex);
			variations.add(variation);
			endIndex++;
		}
		
		return variations;
	}

	private List<String> getT9Equavalents(String fullname) {
		List<String> t9Equavalents = new LinkedList<String>();
		String fullnameEquavalent = "";
		String[] names = fullname.split(" ");
		for (String name : names) {
			String equavalent = getT9Equavalent(name);
			t9Equavalents.add(equavalent);
			fullnameEquavalent += equavalent;
		}
		
		if (!fullnameEquavalent.equals("")) {
			t9Equavalents.add(fullnameEquavalent);
		}
		
		return t9Equavalents;
	}

	private String getT9Equavalent(String name) {
		String variation = "";
		name = name.toLowerCase();
		name = name.replaceAll("[^abc\u00e7defg\u011fh\u0131ijklmno\u00f6pqrs\u015ftu\u00fcwxvyz]", "");
		char[] characters = name.toCharArray();
		for (char c : characters) {
			switch (c) {
			case 'a':
			case 'b':
			case 'c':
			case '\u00e7':
				variation += "2";
				break;
			case 'd':
			case 'e':
			case 'f':
				variation += "3";
				break;
			case 'g':
			case 'h':
			case 'i':
			case '\u0131':
			case '\u011f':
				variation += "4";
				break;
			case 'j':
			case 'k':
			case 'l':
				variation += "5";
				break;
			case 'm':
			case 'n':
			case 'o':
			case '\u00f6':
				variation += "6";
				break;
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case '\u015f':
				variation += "7";
				break;
			case 't':
			case 'u':
			case 'v':
			case '\u00fc':
				variation += "8";
				break;
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				variation += "9";
				break;
			}
		}
		
		return variation;
	}
	
	private Set<String> getNumberVariations(String number) {
		Set<String> variations = new HashSet<>();
		int length = number.length();
		if (length > 0) {
			int beginIndex = 0;
			int endIndex = beginIndex + MIN_NUMBER_LENGTH;
			int iterator = 0;

			while ((endIndex - beginIndex) <= length) {
				
				// move range to right to select substrings
				while (endIndex <= length) {
					String variation = number.substring(beginIndex, endIndex);
					variations.add(variation);
					
					beginIndex++;
					endIndex++;
				}
				
				iterator++;
				beginIndex = 0;
				endIndex = beginIndex + MIN_NUMBER_LENGTH + iterator;
			}
		}
		return variations;
	}

	public List<Contact> filterNumbers(String phrase) {
		LinkedList<Contact> filteredContacts = new LinkedList<>();
		if (phrase != null) {
			Set<Integer> keys = new TreeSet<>();
			if (phrase.length() >= MIN_NUMBER_LENGTH) {
				Set<Integer> numberKeys = numberIndex.get(phrase);
				if (numberKeys != null) {
					keys.addAll(numberKeys);
				}
				Set<Integer> t9Keys = t9Index.get(phrase);
				if (t9Keys != null) {
					keys.addAll(t9Keys);
				}
			}
			else {
				Set<Integer> t9Keys = t9Index.get(phrase);
				if (t9Keys != null) {
					keys.addAll(t9Keys);
				}
			}

			for (Integer key : keys) {
				filteredContacts.add(contacts[key]);
			}
		}
		
		return filteredContacts;
	}
	
	
	public List<Contact> filterNames(String phrase) {
		LinkedList<Contact> filteredContacts = new LinkedList<>();
		if (phrase != null) {
			Set<Integer> keys = nameIndex.get(phrase);
			if (keys != null) {
				for (Integer key : keys) {
					filteredContacts.add(contacts[key]);
				}
			}
		}
		
		return filteredContacts;
	}
	
	@Override
	public String toString() {
		return "Name Index Size:" + nameIndex.size() + "\n" +
				"T9 Index Size:" + t9Index.size() + "\n" +
				"Number Index Size:" + numberIndex.size();
	}
}
