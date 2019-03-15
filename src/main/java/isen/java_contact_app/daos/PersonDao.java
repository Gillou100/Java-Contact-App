package isen.java_contact_app.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isen.java_contact_app.daos.DataSourceFactory;
import isen.java_contact_app.entities.Person;
import isen.java_contact_app.entities.User;
import isen.java_contact_app.util.Category;
import javafx.collections.ObservableList;
import isen.java_contact_app.entities.Address;

public class PersonDao {
	
	
	public static ObservableList<Person> personListByCurrentUser(User currentUser) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * Mise à jour de la liste de personnes filtrées
	 * @param : textFilter -> texte entré comparé au surnom, nom et prénom de chaque utilisateur (+ combinaison nom/prénom dans les 2 sens)
	 * 			categoryFilter -> Categorie sélectionnée comparée à celle de chaque personne
	 */
	public static ObservableList<Person> personListByCurrentUserWithFilters(String textFilter, Category categoryFilter, User currentUser) {
		Person person = new Person();
		if (textFilter == null || textFilter.isEmpty()
				|| person.getNickname().toLowerCase().contains(textFilter) || person.getLastName().toLowerCase().contains(textFilter) || person.getFirstName().toLowerCase().contains(textFilter)
				|| (person.getFirstName().toLowerCase() + " " + person.getLastName().toLowerCase()).contains(textFilter)
				|| (person.getLastName().toLowerCase() + " " + person.getFirstName().toLowerCase()).contains(textFilter)) {
			if (person.getCategory() != null && categoryFilter.equals(person.getCategory()) || categoryFilter.equals(Category.ALL)) {
				//person good
			}
		}
		return null;
	}
	
		
	/*
	 * Ajout d'une nouvelle personne à la liste
	 * @param : person -> la personne à ajouter
	 * 			newPerson -> Si la personne ajoutée l'est seulement pour la liste filtrée ou pour la database aussi
	 */
	public static void addPerson(Person person, User currentUser) {
	}
	
	/*
	 * Supprimer une personne
	 * @param : person -> La personne a supprimé de la liste filtrée et de la database
	 */
	public static void deletePerson(Person person, User currentUser) {
	}
}
