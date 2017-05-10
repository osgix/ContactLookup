/*
 * Copyright 2016 oguzhan acargil
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.contacts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;

/**
 * @author TTOACARGIL
 *
 */
public class ContactsReader2 {

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("FakeContacts.vcf"));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			output(buffer.toString());
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		output("");
	}

	public static void output(String data) {
		VCardEngine engine = new VCardEngine();
		try {
			VCard vcard = engine.parse(data);

			System.out.println("VARD: " + vcard);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (VCardParseException e) {
			e.printStackTrace();
		}

	}

}
