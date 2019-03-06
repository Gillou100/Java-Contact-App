package isen.java_contact_app.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Person{
	// Pour l'adresse : recréer une classe pour séprarer proprement boite Postale, numéro, rue...
	
	private int idperson;
	private String lastname;
	private String firstname;
	private String nickname;
	private String phone_number;
	private String address;
	private String email_address;
	private LocalDate birth_date;
	
	public Person() {
		this.lastname = "";
		this.firstname = "";
		this.nickname = "";
	}
	
	public Person(int idperson, String lastname, String firstname, String nickname, String phone_number, String address, String email_address, LocalDate birth_date) {
		this.idperson = idperson;
		this.lastname = lastname;
		this.firstname = firstname;
		this.nickname = nickname;
		this.phone_number = phone_number;
		this.address = address;
		this.email_address = email_address;
		this.birth_date = birth_date;
	}
	
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}	
	
	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmailAddress(String email_address) {
		this.email_address = email_address;
	}
	
	public void setBirthDate(LocalDate birth_date) {
		this.birth_date = birth_date;
	}
	
	public int getIdperson()
	{
		return idperson;
	}
	
	public String getLastName() {
		return lastname;
	}
	
	public String getFirstName() {
		return firstname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getPhoneNumber() {
		return phone_number;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getEmailAddress() {
		return email_address;
	}
	
	public LocalDate getBirthDate() {
		return birth_date;
	}
	
	public final void export()																											{
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(getFirstName() + "_" + getLastName() + ".vcf"), "UTF-8"))		{
			String string = "BEGIN:VCARD" + "\n"																						;
			string += "VERSION:4.0" + "\n"																								;
			string += "UID:" + getIdperson() + "\n"																						;
			//string += "PHOTO:" + getPicture() + "\n"																					;
			string += "N:" + getLastName() + ";" + getFirstName() + ";" + "" + ";" + "" +  ";" + "" + "\n"								;
			string += "FN:" + getFirstName() + " " + getLastName() + "\n"																;
			string += "NICKNAME:" + getNickname() + "\n"																				;
			string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhoneNumber() + "\n"											;
			string += "EMAIL:" + getEmailAddress() + "\n"																				;
			string += "ADR;" + "TYPE=HOME," + getAddress() + "\n"																		;
			//string += "CATEGORIES:" + String.join(",", getCategories()) + "\n"														;
			string += "BDAY:" + getBirthDate() + "\n"																					;
			string += "END:VCARD"																										;
			writer.write(string)																										;}
		catch(Exception e)																												{
			e.printStackTrace()																											;}}
	
	public static final LinkedList<Person> importDirectory() throws IOException															{
		LinkedList<Person> ListPersons = new LinkedList<>()																				;
		try (DirectoryStream<Path> listOfPaths = Files.newDirectoryStream(Paths.get("")))												{
			for(Path element : listOfPaths)																								{
				if(element.getFileName().toString().endsWith(".vcf"))																	{
					ListPersons.add(importFile(element.getFileName()))																	;}}}
		return ListPersons																												;}
	
	private static final Person importFile(Path file) throws IOException																{	
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8)															;
		Iterator<String> line = lines.iterator()																						;
		
		if(!line.hasNext())																												{
			new IOException("The file " + file + " is empty.")																			;}
		
		int id = -1																														;
		String lastname = null																											;
		String firstname = null																											;
		String nickname = null																											;
		String phone_number = null																										;
		String address = null																											;
		String email_address = null																										;
		LocalDate birth_date = null																										;
		
		while(line.hasNext())																											{
			String data = line.next()																									;
			if(data.equals("BEGIN:VCARD") || data.equals("END:VCARD") || data.regionMatches(0, "FN:", 0, 3))							{
				continue																												;}
			
			String[] dataSeperate = data.split(":")																						;
			switch (dataSeperate[0])																									{
				case "VERSION"																											:
					if(!dataSeperate[1].equals("4.0"))																					{
						new IOException("The file " + file + " is in a wrong version.")													;}
					break																												;
				case "UID"																												:
					id = Integer.parseInt(dataSeperate[1])																				;
					break																												;
				case "N"																												:
					dataSeperate = dataSeperate[1].split(";")																			;
					lastname = dataSeperate[0]																							;
					firstname = dataSeperate[1]																							;
					break																												;
				case "NICKNAME"																											:
					nickname = dataSeperate[1]																							;
					break																												;
				case "EMAIL"																											:
					email_address = dataSeperate[1]																						;
					break																												;
				case "BDAY"																												:
					birth_date = LocalDate.parse(dataSeperate[1])																		;
					break																												;}
			
			dataSeperate = data.split(";")																								;
			switch (dataSeperate[0])																									{
				case "TEL":
					phone_number = dataSeperate[2].substring("VALUE=uri:tel:".length())													;
					break																												;
				case "ADR"																												:
					address = dataSeperate[1].substring("TYPE=HOME,".length())															;
					break																												;}}
		
		return new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date)							;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()																											{
		String string = "\n"																											;
		string += "BEGIN:VCARD" + "\n"																									;
		string += "VERSION:4.0" + "\n"																									;
		string += "UID:" + getIdperson() + "\n"																							;
		//string += "PHOTO:" + getPicture() + "\n"																						;
		string += "N:" + getLastName() + ";" + getFirstName() + ";" + "" + ";" + "" +  ";" + "" + "\n"									;
		string += "FN:" + getFirstName() + " " + getLastName() + "\n"																	;
		string += "NICKNAME:" + getNickname() + "\n"																					;
		string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhoneNumber() + "\n"												;
		string += "EMAIL:" + getEmailAddress() + "\n"																					;
		string += "ADR;" + "TYPE=HOME," + getAddress() + "\n"																			;
		//string += "CATEGORIES:" + String.join(",", getCategories()) + "\n"															;
		string += "BDAY:" + getBirthDate() + "\n"																						;
		string += "END:VCARD"																											;
		return string																													;}
}