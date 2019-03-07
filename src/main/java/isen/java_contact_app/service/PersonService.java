package isen.java_contact_app.service;

import isen.java_contact_app.model.Address;
import isen.java_contact_app.model.Category;
import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.Hashtable;

public class PersonService{
	
	private ObservableList<Person> persons;
	
	private PersonService() {
		persons = FXCollections.observableArrayList();
		Hashtable<Category, Boolean> categories1 = new Hashtable<>(Category.values().length);
		Hashtable<Category, Boolean> categories2 = new Hashtable<>(Category.values().length);
		Hashtable<Category, Boolean> categories3 = new Hashtable<>(Category.values().length);
		for (Category category : Category.values())
		{
			categories1.put(category, false);
			categories1.put(category, false);
			categories1.put(category, false);
		}
		categories1.put(Category.FAMILY, true);
		categories3.put(Category.WORK, true);
		for (int i = 0; i<1; i++) {
			persons.add(new Person(1, "lastname1", "firstname1", "nickname1", "phone_number_1", new Address("", "", "address1", "", "", -1, ""), "email_address_1", LocalDate.now(), categories1));
			persons.add(new Person(1, "lastname2", "firstname2", "nickname2", "phone_number_2", new Address("", "", "address2", "", "", -1, ""), "email_address_2", LocalDate.now(), categories2));
			persons.add(new Person(1, "lastname3", "firstname3", "nickname3", "phone_number_3", new Address("", "", "address3", "", "", -1, ""), "email_address_3", LocalDate.now(), categories3));
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