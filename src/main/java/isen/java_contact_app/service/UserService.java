package isen.java_contact_app.service;

import isen.java_contact_app.model.User;
import isen.java_contact_app.service.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class UserService{

	private ObservableList<User> users;
	
	private User currentUser;
	
	private UserService() {
		users = FXCollections.observableArrayList();
		users.add(new User("root", "root", PersonService.newInstance()));
	}
	
	public static void setCurrentUser(User user) {
		UserServiceHolder.INSTANCE.currentUser = user;
	}
	
	public static User getCurrentUser() {
		return UserServiceHolder.INSTANCE.currentUser;
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