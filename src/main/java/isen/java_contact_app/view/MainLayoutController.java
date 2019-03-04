package isen.java_contact_app.view;

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
		System.out.println("exportation des données");	// Use DirectoryChooser / FileChooser to import
	}

}
