package isen.java_contact_app.model;

import java.time.LocalDate;

public class Person{
	
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
	
	public int getIDPerson() {
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
	
}