package isen.java_contact_app.view;

import isen.java_contact_app.model.User;
import isen.java_contact_app.service.ViewService;
import isen.java_contact_app.service.PersonService;
import isen.java_contact_app.service.StageService;
import isen.java_contact_app.service.UserService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class HomeScreenController {

	@FXML
	TextField usernameField;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	Button connexionUserButton;
	
	@FXML
	Button newUserButton;
	
	@FXML
	Text errorText;
	
	ObservableList<User> users;
	
	boolean newUser;
	
	/*
	 * Cas 1 : Vérifie les informations entrées afin de donner l'accès à la database correspondate de l'utilisateur ou non
	 * Cas 2 : Crée un nouvel utilisateur et lui crée une database
	 */
	@FXML
	public void handleContinueButton() throws Exception {
		System.out.println("vérifier les informations entrées");
		String username = usernameField.getText();
		String password = passwordField.getText();
		boolean existingUser = false;
		
		if (username == null || username.isEmpty()) {
			this.errorText.setText("No username has been entered");
		}
		else if (password == null || password.isEmpty()) {
			this.errorText.setText("No password has been entered");
		}
		
		else if (this.newUser == false) {
			User user;
			for (int i = 0; i<this.users.size(); i++) {
				user = this.users.get(i);
				if (user.getUsername().equals(username)) {
					existingUser = true;
					if (user.getPassword().equals(password)) {
						UserService.setCurrentUser(user);
						PersonService.changeInstance(i);
						StageService.showView(ViewService.getView("ContactOverview"));
					}
					else {
						this.errorText.setText("Password incorrect");
						break;
					}
				}
				System.out.println("oui");
			}
			if (!existingUser) {
				this.errorText.setText("This username don't exist");
			}
		}
		
		else {
			if (password.length() < 6) {
				this.errorText.setText("Password too short (6 characters minimum)");
			}
			else {
				for (int i = 0; i<this.users.size(); i++) {
					if (this.users.get(i).getUsername().equals(username)) {
						this.errorText.setText("This username already exists");
						existingUser = true;
						break;
					}
				}
				if (!existingUser) {
					User user = new User(username, password, PersonService.newInstance());
					this.users.add(user);
					UserService.setCurrentUser(user);
					StageService.showView(ViewService.getView("ContactOverview"));
				}
			}
		}
	}
	
	/*
	 * Cas 1 : Passer à la présentation de nouvel utilisateur
	 * Cas 2 : Passer à la présentation de login d'un utilisateur existant
	 */
	@FXML
	public void handleNewUserButton() throws Exception {
		System.out.println("Ajouter nouvelle utilisateur");
		if (this.newUser == false) {
			this.newUser = true;
			this.errorText.setText(null);
			this.connexionUserButton.setText("Create");
			this.newUserButton.setText("Cancel");
			this.usernameField.setText(null);
			this.passwordField.setText(null);
			this.usernameField.setPromptText("Enter an username");
			this.passwordField.setPromptText("Enter a password");
		}
		else {
			this.newUser = false;
			this.errorText.setText(null);
			this.connexionUserButton.setText("Connexion");
			this.newUserButton.setText("New user ?");
			this.usernameField.setText(null);
			this.passwordField.setText(null);
			this.usernameField.setPromptText(null);
			this.passwordField.setPromptText(null);
		}
	}

	@FXML
	private void initialize() {
		this.users = UserService.getUsers();
		this.newUser = false;
	}
	
	
}
