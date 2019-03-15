package isen.java_contact_app.model;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
import java.time.LocalDate;
////import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;

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