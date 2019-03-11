package isen.java_contact_app.service;

import isen.java_contact_app.model.Address;
import isen.java_contact_app.model.Category;
import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
//import java.util.Hashtable;

public class PersonService{
	
	private ObservableList<Person> persons;

	private ObservableList<Person> personsDataBase;
	
	private PersonService() {
		personsDataBase = FXCollections.observableArrayList();
		persons = FXCollections.observableArrayList();
		/*Hashtable<Category, Boolean> categories1 = new Hashtable<>(Category.values().length);
		Hashtable<Category, Boolean> categories2 = new Hashtable<>(Category.values().length);
		Hashtable<Category, Boolean> categories3 = new Hashtable<>(Category.values().length);
		for (Category category : Category.values())
		{
			categories1.put(category, false);
			categories1.put(category, false);
			categories1.put(category, false);
		}
		categories1.put(Category.FAMILY, true);
		categories3.put(Category.WORK, true);*/
		for (int i = 0; i<1; i++) {
			personsDataBase.add(new Person(1, "lastname1", "firstname1", "nickname1", "0665487562", new Address("", "", "address1", "", "", -1, ""), "email_address_1", LocalDate.now(), Category.FAMILY/*categories1*/, null));
			personsDataBase.add(new Person(1, "lastname2", "firstname2", "nickname2", "0654423025", new Address("", "", "address2", "", "", -1, ""), "email_address_2", LocalDate.now(), null/*categories2*/, null));
			personsDataBase.add(new Person(1, "lastname3", "firstname3", "nickname3", "0744512325", new Address("", "", "address3", "", "", -1, ""), "email_address_3", LocalDate.now(), Category.WORK/*categories3*/, "C"));
		}
	}
	
	public static void filterPersons(String textFilter, Category categoryFilter) {
		if(PersonServiceHolder.INSTANCE.persons != null) PersonService.clearPersons();
		for (Person person : PersonServiceHolder.INSTANCE.personsDataBase) {
			if (textFilter == null || textFilter.isEmpty()
					|| person.getNickname().toLowerCase().contains(textFilter) || person.getLastName().toLowerCase().contains(textFilter) || person.getFirstName().toLowerCase().contains(textFilter)
					|| (person.getFirstName().toLowerCase() + " " + person.getLastName().toLowerCase()).contains(textFilter)
					|| (person.getLastName().toLowerCase() + " " + person.getFirstName().toLowerCase()).contains(textFilter)) {
				if (person.getCategory() != null && categoryFilter.equals(person.getCategory()) || categoryFilter.equals(Category.ALL)) {
					PersonService.addPerson(person, false);
				}
			}
		}
	}
	
	public static ObservableList<Person> getPersons() {
		return PersonServiceHolder.INSTANCE.persons;
	}
	
	public static void addPerson(Person person, boolean newPerson) {
		if(newPerson) PersonServiceHolder.INSTANCE.personsDataBase.add(person);
		PersonServiceHolder.INSTANCE.persons.add(person);
	}
	
	public static void deletePerson(Person person) {
		PersonServiceHolder.INSTANCE.personsDataBase.remove(person);
		PersonServiceHolder.INSTANCE.persons.remove(person);
	}
	
	public static void clearPersons() {
		if(PersonServiceHolder.INSTANCE.persons != null) PersonServiceHolder.INSTANCE.persons.clear();
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