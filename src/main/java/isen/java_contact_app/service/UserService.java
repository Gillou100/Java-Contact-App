package isen.java_contact_app.service;

import isen.java_contact_app.model.User;
import isen.java_contact_app.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class UserService{

	private ObservableList<User> users;
	
	private User currentUser;
	
	/*
	 * Initialise la liste des utilisateurs
	 */
	private UserService() {
		users = FXCollections.observableArrayList();
		users.add(new User("root", "root", PersonService.newInstance()));
	}
	
	/*
	 * Mettre à jour l'utilisateur actuellement connecté
	 * @param : user -> Utilisateur actuellement connecté
	 */
	public static void setCurrentUser(User user) {
		UserServiceHolder.INSTANCE.currentUser = user;
	}
	
	/*
	 * Retourne l'utilisateur actuellement connecté
	 */
	public static User getCurrentUser() {
		return UserServiceHolder.INSTANCE.currentUser;
	}
	
	/*
	 * Retourne la liste des utilisateurs
	 */
	public static ObservableList<User> getUsers() {
		return UserServiceHolder.INSTANCE.users;
	}
	
	/*
	 * Ajoute un nouvel utilisateur
	 * @param : user -> Utilisateur à ajouter à la liste
	 */
	public static void addUser(User user) {
		UserServiceHolder.INSTANCE.users.add(user);
	}
	
	/*
	 * Création d'une sous-classe afin de créer des instances
	 */
	private static class UserServiceHolder {
		private static final UserService INSTANCE = new UserService();
	}
	
}