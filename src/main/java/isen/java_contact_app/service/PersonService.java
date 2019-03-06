package isen.java_contact_app.service;

import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class PersonService{
	
	private ObservableList<Person> persons;
	
	private PersonService() {
		persons = FXCollections.observableArrayList();
		for (int i = 0; i<1; i++) {
			persons.add(new Person(1, "lastname1", "firstname1", "nickname1", "phone_number_1", "address1", "email_address_1", LocalDate.now()));
			persons.add(new Person(1, "lastname2", "firstname2", "nickname2", "phone_number_2", "address2", "email_address_2", LocalDate.now()));
			persons.add(new Person(1, "lastname3", "firstname3", "nickname3", "phone_number_3", "address3", "email_address_3", LocalDate.now()));
		}
	}
	
	public static ObservableList<Person> getPersons() {
		return PersonServiceHolder.INSTANCE.persons;
	}
	
	public static void addPerson(Person person) {
		PersonServiceHolder.INSTANCE.persons.add(person);
	}
	
	public static void deleteListPerson() {
		PersonServiceHolder.INSTANCE.persons.clear();
	}
	
	public static PersonService newInstance() {
		return PersonServiceHolder.INSTANCE = new PersonService();
	}
	
	public static void changeInstance(int indexInstance) {
		PersonServiceHolder.INSTANCE = UserService.getUsers().get(indexInstance).getPersonServiceInstance();
	}
	
	private static class PersonServiceHolder {
		private static PersonService INSTANCE;
	}
	
}