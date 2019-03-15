package isen.java_contact_app.view;

import isen.java_contact_app.entities.Person;
import isen.java_contact_app.service.PersonService;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import isen.java_contact_app.service.StageService;
import isen.java_contact_app.service.VcardService;
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
	
	/*
	 * Ferme la fenêtre
	 */
	public void closeApplication() {
		StageService.closeStage();
	}

	/*
	 * Retourne à l'écran d'accueil
	 */
	public void changeUser() {
		PersonService.setCurrentUser(null);
		StageService.showView(ViewService.getView("HomeScreen"));
	}

	/*
	 * Crée le dossier pour l'export des personens
	 */
	public void exportData()
	{
		System.out.println("exportation des données");
		File directory = dataFolder("export");
		if (directory != null) {
			PersonService.getCurrentUser().setPathFolderContact(directory.getPath());
			File newDirectory = new File(directory, LocalDate.now().toString());
			int numberInstanceDirectory = 2;
			while (newDirectory.exists()) {
				newDirectory = new File(directory, LocalDate.now().toString() + " " + numberInstanceDirectory);
				numberInstanceDirectory++;
			}
			newDirectory.mkdirs();
			for(Person person : PersonService.personList())
			{
				VcardService.export(newDirectory, person);
			}
		}
	}
	
	/*
	 * Récupére chaque fichier VCard (.vcf) du dossier sélectioné	
	 */
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
				PersonService.getCurrentUser().setPathFolderContact(directory.getParent());
				File[] files = directory.listFiles();
				PersonService.clearFilters();
				for (int i = 0; i < files.length; i++) {
					if (files[i].getAbsolutePath().endsWith("vcf")) {
						PersonService.addPerson(VcardService.importFile(files[i]));
					}
				}
			}
    	}
	}
	
	/*
	 * Permet d'ouvrir l'explorateur de dossier et de sélectionner celui voulu
	 * @param : action -> Personnaliser le message de titre de la fenêtre
	 */
	public File dataFolder(String action) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select the contact's folder to " + action);
		directoryChooser.setInitialDirectory(new File(PersonService.getCurrentUser().getPathFolderContact()));
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
	
	/*
	 * Mettre à jour le menu selon la vue actuelle de l'interface
	 */
	public void updateMenu() {
		if (ViewService.actualView == "HomeScreen") {
			visibleMenu(false);
		}
		else if (ViewService.actualView == "ContactOverview") {
			visibleMenu(true);
		}		
	}
	
	/*
	 * Afficher ou non certains éléments du menu
	 * @param : visible -> les éléments seront visibles
	 */
	private void visibleMenu(boolean visible) {
		importDataMenuItem.setVisible(visible);
		exportDataMenuItem.setVisible(visible);
		changeUserMenuItem.setVisible(visible);
		separatorMenuItem.setVisible(visible);
	}

}