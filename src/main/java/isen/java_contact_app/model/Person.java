package isen.java_contact_app.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class Person{
	
	private int idperson;
	private String lastname;
	private String firstname;
	private String nickname;
	private String phone_number;
	private Address address;
	private String email_address;
	private LocalDate birth_date;
	private Category category;
	
	public Person() {
		this.lastname = "";
		this.firstname = "";
		this.nickname = "";
		this.address = new Address();
	}
	
	public Person(int idperson, String lastname, String firstname, String nickname, String phone_number, Address address, String email_address, LocalDate birth_date, Category category) {
		this.idperson = idperson;
		this.lastname = lastname;
		this.firstname = firstname;
		this.nickname = nickname;
		this.phone_number = phone_number;
		this.address = address;
		this.email_address = email_address;
		this.birth_date = birth_date;
		this.category = category;
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

	public void setAddress(Address address) {
		this.address = address;
	}

	public final void setBoitePostale(String boitePostale)
	{
		
		address = new Address(boitePostale, getAdresseEtendue(), getRue(), getVille(), getRegionEtatProvince(), getCodePostal(), getPays());
	}
	
	public final void setAdresseEtendue(String adresseEtendue)
	{
		address = new Address(getBoitePostale(), adresseEtendue, getRue(), getVille(), getRegionEtatProvince(), getCodePostal(), getPays());
	}
	
	public final void setRue(String rue)
	{
		address = new Address(getBoitePostale(), getAdresseEtendue(), rue, getVille(), getRegionEtatProvince(), getCodePostal(), getPays());
	}
	
	public final void setVille(String ville)
	{
		address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), ville, getRegionEtatProvince(), getCodePostal(), getPays());
	}
	
	public final void setRegionEtatProvince(String regionEtatProvince)
	{
		address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), getVille(), regionEtatProvince, getCodePostal(), getPays());
	}
	
	public final void setCodePostal(int codePostale)
	{
		address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), getVille(), getRegionEtatProvince(), codePostale, getPays());
	}
	
	public final void setPays(String pays)
	{
		address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), getVille(), getRegionEtatProvince(), getCodePostal(), pays);
	}
	
	public void setEmailAddress(String email_address) {
		this.email_address = email_address;
	}
	
	public void setBirthDate(LocalDate birth_date) {
		this.birth_date = birth_date;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public int getIDPerson()
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
	
	public Address getAddress() {
		return address;
	}
	
	public Category getCategory() {
		return category;
	}
	
	/**
	 * @return the boitePostale
	 */
	public final String getBoitePostale()
	{
		return getAddress().getBoitePostale();
	}
	
	/**
	 * @return the adresseEtendue
	 */
	public final String getAdresseEtendue()
	{
		return getAddress().getAdresseEtendue();
	}
	
	/**
	 * @return the rue
	 */
	public final String getRue()
	{
		return getAddress().getRue();
	}
	
	/**
	 * @return the ville
	 */
	public final String getVille()
	{
		return getAddress().getVille();
	}
	
	/**
	 * @return the regionEtatProvince
	 */
	public final String getRegionEtatProvince()
	{
		return getAddress().getRegionEtatProvince();
	}
	
	/**
	 * @return the codePostal
	 */
	public final int getCodePostal()
	{
		return getAddress().getCodePostal();
	}
	
	/**
	 * @return the pays
	 */
	public final String getPays()
	{
		return getAddress().getPays();
	}
	
	public String getEmailAddress() {
		return email_address;
	}
	
	public LocalDate getBirthDate() {
		return birth_date;
	}
	
	public final void export(File directory)
	{
		File file = new File(directory, getFirstName() + "_" + getLastName() + ".vcf");
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(file.toString()), "UTF-8"))
		{
			String string = "BEGIN:VCARD" + "\n";
			string += "VERSION:4.0" + "\n";
			string += "UID:" + getIDPerson() + "\n";
			//string += "PHOTO:" + getPicture() + "\n;
			string += "N:" + getLastName() + ";" + getFirstName() + ";" + "" + ";" + "" +  ";" + "" + "\n";
			string += "FN:" + getFirstName() + " " + getLastName() + "\n";
			string += "NICKNAME:" + getNickname() + "\n";
			string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhoneNumber() + "\n";
			string += "EMAIL:" + getEmailAddress() + "\n";
			string += "ADR;" + "TYPE=HOME;" + "LABEL=" + getAddress() + ":" + ";" + ";" + getRue() + ";" + getVille() + ";" + getRegionEtatProvince() + ";" + getCodePostal() + ";" + getPays() + "\n";
			//string += "CATEGORIES:" + String.join(",", getCategories()) + "\n";
			string += "BDAY:" + getBirthDate() + "\n";
			string += "END:VCARD";
			writer.write(string);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static final Person importFile(File file) throws IOException		// Mettre à jour la catégorie
	{
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		Iterator<String> line = lines.iterator();
		
		if(!line.hasNext())
		{
			new IOException("The file " + file + " is empty.");
		}
		
		int id = -1;
		String lastname = null;
		String firstname = null;
		String nickname = null;
		String phone_number = null;
		Address address = null;
		String email_address = null;
		LocalDate birth_date = null;
		//String picture = null;
		//String[] categories = null;
		
		while(line.hasNext())
		{
			String data = line.next();
			if(data.equals("BEGIN:VCARD") || data.equals("END:VCARD") || data.regionMatches(0, "FN:", 0, 3))
			{
				continue;
			}
			
			String[] dataSeperate = data.split(":");
			switch (dataSeperate[0])
			{
				case "VERSION":
					if(!dataSeperate[1].equals("4.0"))
					{
						new IOException("The file " + file + " is in a wrong version.");
					}
					break;
				/*case "PHOTO":
					picture = dataSeperate[1];
					break;*/
				case "UID":
					id = Integer.parseInt(dataSeperate[1]);
					break;
				case "N":
					dataSeperate = dataSeperate[1].split(";");
					lastname = dataSeperate[0];
					firstname = dataSeperate[1];
					break;
				case "NICKNAME":
					nickname = dataSeperate[1];
					break;
				case "EMAIL":
					email_address = dataSeperate[1];
					break;
				case "BDAY":
					birth_date = LocalDate.parse(dataSeperate[1]);
					break;
				/*case "CATEGORIES:":
					categories = dataSeperate[1].split(",");
					break;*/
			}
			
			dataSeperate = data.split(";");
			switch (dataSeperate[0])
			{
				case "TEL":
					phone_number = dataSeperate[2].substring("VALUE=uri:tel:".length());
					break;
				case "ADR":
					String boitePostale = (dataSeperate[2].split(":"))[1];
					String adresseEtendue = dataSeperate[3];
					String rue = dataSeperate[4];
					String ville = dataSeperate[5];
					String regionEtatProvince = dataSeperate[6];
					int codePostal = Integer.parseInt(dataSeperate[7]);
					String pays = dataSeperate[8];
					address = new Address(boitePostale, adresseEtendue, rue, ville, regionEtatProvince, codePostal, pays);
					break;
			}
		}
		return new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date, null);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String string = "\n";
		string += "BEGIN:VCARD" + "\n";
		string += "VERSION:4.0" + "\n";
		string += "UID:" + getIDPerson() + "\n";
		//string += "PHOTO:" + getPicture() + "\n";
		string += "N:" + getLastName() + ";" + getFirstName() + ";" + "" + ";" + "" +  ";" + "" + "\n";
		string += "FN:" + getFirstName() + " " + getLastName() + "\n";
		string += "NICKNAME:" + getNickname() + "\n";
		string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhoneNumber() + "\n";
		string += "EMAIL:" + getEmailAddress() + "\n";
		string += "ADR;" + "TYPE=HOME;" + "LABEL=" + getAddress() + ":" + ";" + ";" + getRue() + ";" + getVille() + ";" + getRegionEtatProvince() + ";" + getCodePostal() + ";" + getPays() + "\n";
		//string += "CATEGORIES:" + String.join(",", getCategories()) + "\n";
		string += "BDAY:" + getBirthDate() + "\n";
		string += "END:VCARD";
		return string;
	}
}