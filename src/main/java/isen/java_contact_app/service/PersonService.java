package isen.java_contact_app.service;

import isen.java_contact_app.util.Category;
import javafx.collections.ObservableList;

import isen.java_contact_app.daos.PersonDao;
import isen.java_contact_app.entities.Person;
import isen.java_contact_app.entities.User;

public class PersonService{

	private static User currentUser;
	
	private static String textFilter;
	
	private static Category categoryFilter;
	
	/*
	 * Ajout d'une nouvelle personne à la liste
	 * @param : person -> la personne à ajouter
	 * 			newPerson -> Si la personne ajoutée l'est seulement pour la liste filtrée ou pour la database aussi
	 */
	public static void addPerson(Person person) {
		PersonDao.addPerson(person, PersonService.currentUser);
	}
	
	/*
	 * Supprimer une personne
	 * @param : person -> La personne a supprimé de la liste filtrée et de la database
	 */
	public static void deletePerson(Person person) {
		PersonDao.deletePerson(person, PersonService.currentUser);
	}
	
	public static void setFilter(String textFilter, Category categoryFilter) {
		PersonService.textFilter = textFilter;
		PersonService.categoryFilter = categoryFilter;
	}
	
	/*
	 * Effacer les filtres
	 */
	public static void clearFilters() {
		PersonService.textFilter = "";
		PersonService.categoryFilter = Category.ALL;
	}


	public static ObservableList<Person> personList() {
		if (PersonService.textFilter == "" && PersonService.categoryFilter == Category.ALL)
			return PersonDao.personListByCurrentUser(PersonService.currentUser);
		else
			return PersonDao.personListByCurrentUserWithFilters(
				PersonService.textFilter, 
				PersonService.categoryFilter, 
				PersonService.currentUser);
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		PersonService.currentUser = currentUser;
	}
}