package isen.java_contact_app.view;

import java.util.Observable;

import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.PersonService;
import javafx.collections.ObservableList;
import isen.java_contact_app.service.StageService;
import isen.java_contact_app.service.ViewService;

public class MainLayoutController {

	public void closeApplication() {
		StageService.closeStage();
	}

	public void changeUser() {
		StageService.showView(ViewService.getView("HomeScreen"));
	}

	public void exportData() {
		ObservableList<Person> persons = PersonService.getPersons();
		for (Person person : persons)
		{
			//fichier.vcf
		}
		System.out.println("exportation des données");	// Use DirectoryChooser / FileChooser to import
	}

}