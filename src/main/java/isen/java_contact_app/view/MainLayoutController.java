package isen.java_contact_app.view;

import isen.java_contact_app.model.Person;
import isen.java_contact_app.service.PersonService;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import isen.java_contact_app.service.StageService;
import isen.java_contact_app.service.UserService;
import isen.java_contact_app.service.ViewService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.DirectoryChooser;

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
		UserService.setCurrentUser(null);
		StageService.showView(ViewService.getView("HomeScreen"));
	}

	public void exportData()
	{
		System.out.println("exportation des données");
		File directory = dataFolder("export");
		if (directory != null) {
			UserService.getCurrentUser().setPathFolderContact(directory.getPath());
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
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initOwner(StageService.getPrimaryStage());
		alert.setTitle("INFORMATION");
		alert.setHeaderText("Don't forget to make a backup/export of the current database," + "\n" + "Make an import will permanently replace it." + "\n" + "Continue?");
		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == ButtonType.OK) {
			File directory = dataFolder("import");
			if (directory != null) {
				UserService.getCurrentUser().setPathFolderContact(directory.getParent());
				File[] files = directory.listFiles();
				PersonService.deleteListPerson();
				for (int i = 0; i < files.length; i++) {
					if (files[i].getAbsolutePath().endsWith("vcf")) {
						PersonService.addPerson(Person.importFile(files[i]));
					}
				}
			}
    	}
	}
	
	
	public File dataFolder(String action) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select the contact's folder to " + action);
		directoryChooser.setInitialDirectory(new File(UserService.getCurrentUser().getPathFolderContact()));
		File directory = null;
        try{
        	try{
	        	directory = directoryChooser.showDialog(StageService.getPrimaryStage());
	        }catch(IllegalArgumentException e){
				directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	    		directory = directoryChooser.showDialog(StageService.getPrimaryStage());
	        }
	    }catch (Exception e) {
	    	e.printStackTrace();
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

}