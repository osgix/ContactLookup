package com.example.contacts;

import java.util.LinkedList;
import java.util.List;


public class Contact {

	private String fullname;
	private List<String> numbers;
	
	public Contact() {
		this.numbers = new LinkedList<>();
	}
	
	public boolean isValid() {
		if (fullname != null && !fullname.equals("")) {
			return !numbers.isEmpty();
		}
		else {
			return false;
		}
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void addNumber(String number) {
		numbers.add(number);
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public List<String> getNumbers() {
		return numbers;
	}

	@Override
	public String toString() {
		return "Contact [fullname=" + fullname + ", numbers=" + numbers + "]";
	}
	
}
