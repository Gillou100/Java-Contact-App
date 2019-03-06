package isen.java_contact_app.view;

import java.util.Observable;

import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.PersonService;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import isen.java_contact_app.service.StageService;
import isen.java_contact_app.service.ViewService;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainLayoutController {
	
	@FXML
	MenuItem importDataMenuItem;
	
	@FXML
	MenuItem exportDataMenuItem;
	
	@FXML
	MenuItem changeUserMenuItem;
	
	@FXML
	SeparatorMenuItem separatorMenuItem;
	
	String pathFolder;
	
	public void closeApplication() {
		StageService.closeStage();
	}

	public void changeUser() {
		StageService.showView(ViewService.getView("HomeScreen"));
	}

	public void exportData()
	{
		System.out.println("exportation des données");
		File directory = dataFolder("export");
		System.out.println(directory);
		if (directory != null) {
			pathFolder = directory.getPath();
			File newDirectory = new File(directory, LocalDate.now().toString());
			int numberInstanceDirectory = 2;
			while (newDirectory.exists()) {
				newDirectory = new File(directory, LocalDate.now().toString() + " " + numberInstanceDirectory);
				numberInstanceDirectory++;
			}
			newDirectory.mkdirs();
			for(Person person : PersonService.getPersons())
			{
				person.export(newDirectory);
			}
		}
	}
	
	
	
	
	public void importData() throws IOException{
		System.out.println("importation des données");
		File directory = dataFolder("import");
		if (directory != null) {
			pathFolder = directory.getParent();
			File[] files = directory.listFiles();		// Contient le chemin absolu de chaque éléments du dossier choisi précédemment
			PersonService.deleteListPerson();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getAbsolutePath().endsWith("vcf")) {
					PersonService.addPerson(Person.importFile(files[i]));
				}
			}
		}
	}
	
	
	public File dataFolder(String action) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select the contact's folder to " + action);
    	directoryChooser.setInitialDirectory(new File(pathFolder));
		File directory;
        try{
        	directory = directoryChooser.showDialog(StageService.getPrimaryStage());
        }catch(IllegalArgumentException e) {
        	directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        	directory = directoryChooser.showDialog(StageService.getPrimaryStage());
        }
        return directory;
	}
	
	
	
	
	public void updateMenu() {
		if (ViewService.actualView == "HomeScreen") {
			visibleMenu(false);
		}
		else if (ViewService.actualView == "ContactOverview") {
			visibleMenu(true);
		}		
	}
	
	private void visibleMenu(boolean visible) {
		importDataMenuItem.setVisible(visible);
		exportDataMenuItem.setVisible(visible);
		changeUserMenuItem.setVisible(visible);
		separatorMenuItem.setVisible(visible);
	}
	
	@FXML
	private void initialize() {
		pathFolder = System.getProperty("user.home");
	}

}