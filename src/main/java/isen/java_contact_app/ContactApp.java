package isen.java_contact_app;

import java.io.IOException;
import java.time.LocalDate;

import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.PersonService;
import isen.java_contact_app.service.StageService;
import isen.java_contact_app.service.ViewService;
import javafx.application.Application;
import javafx.stage.Stage;

public class ContactApp extends Application {

	public ContactApp() {

	}

	@Override
	public void start(Stage primaryStage) {
		StageService.initPrimaryStage(primaryStage);
		StageService.showView(ViewService.getView("HomeScreen"));
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
