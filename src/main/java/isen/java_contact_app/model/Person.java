package isen.java_contact_app.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
//import java.util.Hashtable;
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
	//private Hashtable<Category, Boolean> category;
	private String url_photo;
	
	public Person() {
		this.lastname = "";
		this.firstname = "";
		this.nickname = "";
		this.address = new Address();
		/*for (Category categorie : Category.values())
		{
			categories.put(categorie, false);
		}*/
	}
	
	public Person(int idperson, String lastname, String firstname, String nickname, String phone_number, Address address, String email_address, LocalDate birth_date, Category category, String url_photo) {
	//public Person(int idperson, String lastname, String firstname, String nickname, String phone_number, Address address, String email_address, LocalDate birth_date, Hashtable<Category, Boolean> categories, Image url_photo) {
		this.idperson = idperson;
		this.lastname = lastname;
		this.firstname = firstname;
		this.nickname = nickname;
		this.phone_number = phone_number;
		this.address = address;
		this.email_address = email_address;
		this.birth_date = birth_date;
		this.category = category;
		/*for (Category category : categories.keySet())
		{
			this.categories.put(category, categories.get(category));
		}*/
		this.url_photo = url_photo;
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
		//address = new Address(boitePostale, getAdresseEtendue(), getRue(), getVille(), getRegionEtatProvince(), getCodePostal(), getPays());
		address.setBoitePostale(boitePostale);
	}
	
	public final void setAdresseEtendue(String adresseEtendue)
	{
		//address = new Address(getBoitePostale(), adresseEtendue, getRue(), getVille(), getRegionEtatProvince(), getCodePostal(), getPays());
		address.setAdresseEtendue(adresseEtendue);
	}
	
	public final void setRue(String rue)
	{
		//address = new Address(getBoitePostale(), getAdresseEtendue(), rue, getVille(), getRegionEtatProvince(), getCodePostal(), getPays());
		address.setRue(rue);
	}
	
	public final void setVille(String ville)
	{
		//address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), ville, getRegionEtatProvince(), getCodePostal(), getPays());
		address.setVille(ville);
	}
	
	public final void setRegionEtatProvince(String regionEtatProvince)
	{
		//address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), getVille(), regionEtatProvince, getCodePostal(), getPays());
		address.setRegionEtatProvince(regionEtatProvince);
	}
	
	public final void setCodePostal(int codePostale)
	{
		//address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), getVille(), getRegionEtatProvince(), codePostale, getPays());
		address.setCodePostal(codePostale);
	}
	
	public final void setPays(String pays)
	{
		//address = new Address(getBoitePostale(), getAdresseEtendue(), getRue(), getVille(), getRegionEtatProvince(), getCodePostal(), pays);
		address.setPays(pays);
	}
	
	public void setEmailAddress(String email_address) {
		this.email_address = email_address;
	}
	
	public void setBirthDate(LocalDate birth_date) {
		this.birth_date = birth_date;
	}
	
	public void setCategory(Category category)
	{
		this.category = category;
	}
	
	/*public void setCategories(Hashtable<Category, Boolean> categories)
	{
		for(Category category : categories.keySet())
		{
			this.categories.put(category, categories.get(category));
		}
	}

	public void changeCategory(Category newCategory)
	{
		for(Category category : categories.keySet())
		{
			categories.put(category, false);
		}
		categories.put(newCategory, true);
	}

	public void changeCategories(Category[] categories)
	{
		for(Category category : this.categories.keySet())
		{
			this.categories.put(category, false);
		}
		for(Category category : categories)
		{
			this.categories.put(category, true);
		}
	}

	public void addCategory(Category category)
	{
		categories.put(category, true);
	}*/
	
	public void setURLPhoto(String url_photo)
	{
		this.url_photo = url_photo;
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
	
	/*public Hashtable<Category, Boolean> getCategory() {
		return categories;
	}*/
	
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
	
	public String getURLPhoto()
	{
		return url_photo;
	}
	
	public final void export(File directory)
	{
		File file = new File(directory, getFirstName() + "_" + getLastName() + ".vcf");
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(file.toString()), "UTF-8"))
		{
			String string = "BEGIN:VCARD" + "\n";
			string += "VERSION:4.0" + "\n";
			string += "UID:" + getIDPerson() + "\n";
			string += "PHOTO:" + getURLPhoto() + "\n";
			string += "N:" + getLastName() + ";" + getFirstName() + ";" + "" + ";" + "" +  ";" + "" + "\n";
			string += "FN:" + getFirstName() + " " + getLastName() + "\n";
			string += "NICKNAME:" + getNickname() + "\n";
			string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhoneNumber() + "\n";
			string += "EMAIL:" + getEmailAddress() + "\n";
			string += "ADR;" + "TYPE=HOME;" + "LABEL=\"" + getAddress() + "\":" + ";" + ";" + getRue() + ";" + getVille() + ";" + getRegionEtatProvince() + ";" + (getCodePostal() < 0 ? "" : getCodePostal()) + ";" + getPays() + "\n";
			string += "CATEGORIES:" + getCategory() + "\n";
			/*string += "CATEGORIES:" + categories.entrySet().stream()
					.filter(x -> x.getValue().equals(true))
					.map(x -> x.getKey().toString())
					.collect(Collectors.joining(",")) + "\n";*/
			string += "BDAY:" + getBirthDate() + "\n";
			string += "END:VCARD";
			writer.write(string);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static final Person importFile(File file) throws IOException
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
		String picture = null;
		Category category = null;
		/*Hashtable<Category, Boolean> categories = new Hashtable<>(Category.values().length);
		for (Category categorie : Category.values())
		{
			categories.put(categorie, false);
		}*/
		
		while(line.hasNext())
		{
			String data = line.next();
			if(data.equals("BEGIN:VCARD") || data.regionMatches(0, "FN:", 0, 3))
			{
				continue;
			}
			else if(data.equals("END:VCARD"))
			{
				break;
			}
			
			else
			{
				String[] dataSeperate = data.split(":");
				switch (dataSeperate[0])
				{
					case "VERSION":
						if(!dataSeperate[1].equals("4.0"))
						{
							new IOException("The file " + file + " is in a wrong version.");
						}
						break;
					case "PHOTO":
						picture = dataSeperate[1] + ":" + dataSeperate[2] + ":" + dataSeperate[3];
						break;
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
					case "CATEGORIES":
						category = Category.valueOf(dataSeperate[1]);
						/*String[] categoriesString = dataSeperate[1].split(";");
						for(String category: categoriesString)
						{
							if(Category.valueOf(category) != null)
							{
								categories.put(Category.valueOf(category), true);
							}
						}*/
						break;
				}
				
				dataSeperate = data.split(";");
				switch (dataSeperate[0])
				{
					case "TEL":
						phone_number = dataSeperate[2].substring("VALUE=uri:tel:".length());
						break;
					case "ADR":
						String pays = "";
						int codePostal = -1;
						String regionEtatProvince = "";
						String ville = "";
						String rue = "";
						String adresseEtendue = "";
						String boitePostale = "";
						switch(dataSeperate.length)
						{
							case 9:
								pays = dataSeperate[8];
							case 8:
								codePostal = Integer.parseInt(dataSeperate[7]);
							case 7:
								regionEtatProvince = dataSeperate[6];
							case 6:
								ville = dataSeperate[5];
							case 5:
								rue = dataSeperate[4];
							case 4:
								adresseEtendue = dataSeperate[3];
							case 3:
								boitePostale = dataSeperate[2].split(":").length > 1 ? dataSeperate[2].split(":")[1] : "";
						}
						address = new Address(boitePostale, adresseEtendue, rue, ville, regionEtatProvince, codePostal, pays);
						break;
				}
			}
		}
		return new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date, category, picture);
		//return new Person(id, lastname, firstname, nickname, phone_number, address, email_address, birth_date, categories, picture);
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
		string += "PHOTO:" + getURLPhoto() + "\n";
		string += "N:" + getLastName() + ";" + getFirstName() + ";" + "" + ";" + "" +  ";" + "" + "\n";
		string += "FN:" + getFirstName() + " " + getLastName() + "\n";
		string += "NICKNAME:" + getNickname() + "\n";
		string += "TEL;" + "TYPE=home,voice;" + "VALUE=uri:tel:" + getPhoneNumber() + "\n";
		string += "EMAIL:" + getEmailAddress() + "\n";
		string += "ADR;" + "TYPE=HOME;" + "LABEL=" + getAddress() + ":" + ";" + ";" + getRue() + ";" + getVille() + ";" + getRegionEtatProvince() + ";" + (getCodePostal() < 0 ? "" : getCodePostal()) + ";" + getPays() + "\n";
		string += "CATEGORIES:" + getCategory() + "\n";
		/*string += "CATEGORIES:" + categories.entrySet().stream()
				.filter(x -> x.getValue().equals(true))
				.map(x -> x.getKey().toString())
				.collect(Collectors.joining(",")) + "\n";*/
		string += "BDAY:" + getBirthDate() + "\n";
		string += "END:VCARD";
		return string;
	}
}