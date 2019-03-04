package isen.java_contact_app.service;

import isen.java_contact_app.model.User;
import isen.java_contact_app.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserService{
	
	private ObservableList<User> users;
	
	private UserService() {
		users = FXCollections.observableArrayList();
		for (int i = 0; i<1; i++) {
			users.add(new User("root", "root", PersonService.newInstance()));
			//users.add(new User("Jeanflo", "oui"));
			//users.add(new User("Gillou", "non"));
			}
	}
	
	public static ObservableList<User> getUsers() {
		return UserServiceHolder.INSTANCE.users;
	}
	
	public static void addUser(User user) {
		UserServiceHolder.INSTANCE.users.add(user);
	}
	
	private static class UserServiceHolder {
		private static final UserService INSTANCE = new UserService();
	}
	
}