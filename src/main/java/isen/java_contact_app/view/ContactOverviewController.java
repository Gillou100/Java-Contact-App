package isen.java_contact_app.view;


import java.io.File;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.function.UnaryOperator;

import isen.java_contact_app.ContactApp;
import isen.java_contact_app.entities.Person;
import isen.java_contact_app.util.NicknameValueFactory;
import isen.java_contact_app.util.Category;
import isen.java_contact_app.util.NameValueFactory;
import isen.java_contact_app.service.PersonService;
import isen.java_contact_app.service.StageService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.DatePicker;

public class ContactOverviewController{
	
	@FXML
	TableView<Person> personTable;

	@FXML
	TableColumn<Person, String> nicknameColumn;
	
	@FXML
	TableColumn<Person, String> nameColumn;

	@FXML
	TextField lastNameField;

	@FXML
	TextField firstNameField;

	@FXML
	TextField nicknameField;

	@FXML
	TextField phoneNumberField;
	
	@FXML
	TextField streetAddressField;
	
	@FXML
	TextField pcAddressField;
	
	@FXML
	TextField cityAddressField;
	
	@FXML
	TextField regionAddressField;
	
	@FXML
	TextField countryAddressField;

	@FXML
	TextField emailAddressField;
	
	@FXML
	DatePicker birthDatePicker;
	
	@FXML
	ComboBox<Category> categoryComboBox;
	
	@FXML
	ImageView photoImageView;
	
	@FXML
	ImageView changePhotoImageView;
	
	@FXML
	Button addButton;
	
	@FXML
	Button updateButton;
	
	@FXML
	Button deleteButton;
	
	@FXML
	Button saveButton;
	
	@FXML
	Button cancelButton;
	
	@FXML
	ComboBox<Category> filterComboBox;
	
	@FXML
	TextField filterTextField;
	
	@FXML
	Button filterButton;
	
	private final Image defaultPhoto = new Image(ContactApp.class.getResource("image/default-photo.jpg").toString());
	
	private String urlChangePhoto;
	
	Person currentPerson;
	
	private boolean newPerson;
	
	/*
	 * Ajouter une personne dans la liste des contacts
	 */
	@FXML
	private void handleAddButton() {
		Person person = new Person();
		PersonService.addPerson(person);
		this.personTable.getSelectionModel().select(person);
		this.newPerson = true;
		this.handleUpdateButton();
	}
	
	/*
	 * Activer la modification des informations d'une person
	 */
	@FXML
	private void handleUpdateButton() {
		this.updatingPerson(true);
		this.disableButton(false, true);
	}
	
	/*
	 * Supprimer la personne sélectionnée de la liste des contacts
	 */
	@FXML
	private void handleDeleteButton() {
		Person person = this.personTable.getSelectionModel().getSelectedItem();
	    if (person != null) {
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.initOwner(StageService.getPrimaryStage());
			alert.setTitle("WARNING");
			alert.setHeaderText("You are going to delete this contact, continue ?");
			Optional<ButtonType> option = alert.showAndWait();
			if (option.get() == ButtonType.OK) {
				PersonService.deletePerson(person);
		        this.personTable.getSelectionModel().clearSelection();
		        this.disableButton(true, false);
		        this.personTable.refresh();
	    	}
	    }
	}
	
	/*
	 * Sauvegarder les changements sur les informations d'une personne
	 */
	@FXML
	private void handleSaveButton() {
		this.updatingPerson(false);
		this.disableButton(false, false);
		this.personTable.setDisable(false);
		this.savePersonDetails();
		this.personTable.refresh();
	}
	
	/*
	 * Annuler les changements sur les informations d'une personne
	 */
	@FXML
	private void handleCancelButton() {
		this.urlChangePhoto = null;
		this.updatingPerson(false);
		this.disableButton(false, false);
		this.showPersonDetails(currentPerson);
		if (this.newPerson == true) {
			this.newPerson = false;
			this.handleDeleteButton();
		}
	}
	
	/*
	 * Actualise la liste des personnes affichées selon les paramètres de filtrage indiqués
	 */
	@FXML
	private void handleFilterButton() {
		PersonService.setFilter(this.filterTextField.getText().toLowerCase(), this.filterComboBox.getValue());
		this.personTable.refresh();
	}
	
	/*
	 * Initialisation spécifique de certains élèments de l'interface
	 */
	@FXML
	private void initialize() {
		this.nicknameColumn.setCellValueFactory(new NicknameValueFactory());
		this.nameColumn.setCellValueFactory(new NameValueFactory());
		this.personTable.setItems(PersonService.personList());
		PersonService.clearFilters();
		this.personTable.refresh();
		birthDatePicker.setShowWeekNumbers(false);
		
		this.personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {

			@Override
			public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
				disableButton(false, false);
				showPersonDetails(newValue);
			}
		});
		this.categoryComboBox.setItems(Category.initCategoryList(false));
		this.filterComboBox.setItems(Category.initCategoryList(true));
		this.filterComboBox.getSelectionModel().select(Category.ALL);
		
		UnaryOperator<TextFormatter.Change> integerOnlyFilter = change -> {
			String text = change.getText();
			if (text.isEmpty() || text == null) return change;
			for (int i = 0; i<text.length(); i++) {
				if (!String.valueOf(text.charAt(i)).matches("[0-9]")) return null;
			}
			return change;
		};
		
		TextFormatter<Integer> pcAddressIntegerOnlyFormatter = new TextFormatter<Integer>(integerOnlyFilter);
		this.pcAddressField.setTextFormatter(pcAddressIntegerOnlyFormatter);
		TextFormatter<Integer> phoneNumberIntegerOnlyFormatter = new TextFormatter<Integer>(integerOnlyFilter);
		this.phoneNumberField.setTextFormatter(phoneNumberIntegerOnlyFormatter);
		
	}
	
	/*
	 * Afficher les détails de la personne sélectionnée
	 * @param: person -> la personne actuellement sélectionnée
	 */
	private void showPersonDetails(Person person) {
		this.currentPerson = person;
		if (currentPerson == null) {
			this.lastNameField.setText(null);
			this.firstNameField.setText(null);
			this.nicknameField.setText(null);
			this.phoneNumberField.setText(null);
			this.streetAddressField.setText(null);
			this.pcAddressField.setText(null);
			this.cityAddressField.setText(null);
			this.regionAddressField.setText(null);
			this.countryAddressField.setText(null);
			this.emailAddressField.setText(null);
			this.birthDatePicker.setValue(null);
			this.categoryComboBox.setValue(null);
			this.photoImageView.setImage(defaultPhoto);
		}
		else {
			this.lastNameField.setText(currentPerson.getLastName());
			this.firstNameField.setText(currentPerson.getFirstName());
			this.nicknameField.setText(currentPerson.getNickname());
			this.phoneNumberField.setText(currentPerson.getPhoneNumber());
			this.streetAddressField.setText(currentPerson.getRue());
			this.pcAddressField.setText(currentPerson.getCodePostal() == -1 ? null : String.valueOf(currentPerson.getCodePostal()));
			this.cityAddressField.setText(currentPerson.getVille());
			this.regionAddressField.setText(currentPerson.getRegionEtatProvince());
			this.countryAddressField.setText(currentPerson.getPays());
			this.emailAddressField.setText(currentPerson.getEmailAddress());
			this.birthDatePicker.setValue(currentPerson.getBirthDate());
			this.categoryComboBox.setValue(currentPerson.getCategory());
			if (currentPerson.getURLPhoto() == null) {
				this.photoImageView.setImage(defaultPhoto);
			}
			else {
				try {
					this.photoImageView.setImage(new Image(currentPerson.getURLPhoto()));
				}catch(NullPointerException|IllegalArgumentException e) {
					this.photoImageView.setImage(defaultPhoto);
				}
			}
		}
		
	}
	
	/*
	 * Récupérer les différents informations d'une personne afin de les sauvegarder
	 */
	private void savePersonDetails() {
		this.currentPerson.setLastName(lastNameField.getText());
		this.currentPerson.setFirstName(firstNameField.getText());
		this.currentPerson.setNickname(nicknameField.getText());
		this.currentPerson.setPhoneNumber(phoneNumberField.getText());
		this.currentPerson.setRue(streetAddressField.getText());
		this.currentPerson.setCodePostal((pcAddressField.getText() == null || pcAddressField.getText().isEmpty()) ? -1 : Integer.parseInt(pcAddressField.getText()));
		this.currentPerson.setVille(cityAddressField.getText());
		this.currentPerson.setRegionEtatProvince(regionAddressField.getText());
		this.currentPerson.setPays(countryAddressField.getText());
		this.currentPerson.setEmailAddress(emailAddressField.getText());
		this.currentPerson.setBirthDate(birthDatePicker.getValue());
		this.currentPerson.setCategory(categoryComboBox.getValue());
		this.currentPerson.setURLPhoto(this.urlChangePhoto);
		if (this.newPerson == true) {
			this.newPerson = false;
		}
		showPersonDetails(currentPerson);
	}
	
	/*
	 * Rendre l'intéraction avec certains élèments de l'interface possible ou non
	 * @param : editable -> Vrai pour permettre à l'utilisateur de modifier les informations de la personne, faux dans l'autre cas
	 */
	private void updatingPerson(boolean editable) {
		this.lastNameField.setMouseTransparent(!editable);
		this.lastNameField.setEditable(editable);
		this.firstNameField.setMouseTransparent(!editable);
		this.firstNameField.setEditable(editable);
		this.nicknameField.setMouseTransparent(!editable);
		this.nicknameField.setEditable(editable);
		this.phoneNumberField.setMouseTransparent(!editable);
		this.phoneNumberField.setEditable(editable);
		this.streetAddressField.setMouseTransparent(!editable);
		this.streetAddressField.setEditable(editable);
		this.pcAddressField.setMouseTransparent(!editable);
		this.pcAddressField.setEditable(editable);
		this.cityAddressField.setMouseTransparent(!editable);
		this.cityAddressField.setEditable(editable);
		this.regionAddressField.setMouseTransparent(!editable);
		this.regionAddressField.setEditable(editable);
		this.countryAddressField.setMouseTransparent(!editable);
		this.countryAddressField.setEditable(editable);
		this.emailAddressField.setMouseTransparent(!editable);
		this.emailAddressField.setEditable(editable);
		this.birthDatePicker.setMouseTransparent(!editable);
		this.categoryComboBox.setMouseTransparent(!editable);
		this.photoImageView.setMouseTransparent(!editable);
		this.changePhotoImageView.setVisible(editable);
		this.personTable.setMouseTransparent(editable);
		this.filterTextField.setEditable(!editable);
		this.filterTextField.setMouseTransparent(editable);
		this.filterComboBox.setDisable(editable);
		this.filterComboBox.setMouseTransparent(editable);
		if (editable) {
			this.streetAddressField.setPromptText("Street");
			this.pcAddressField.setPromptText("PC");
			this.cityAddressField.setPromptText("City");
			this.regionAddressField.setPromptText("Region");
			this.countryAddressField.setPromptText("Country");
		}
		else {
			this.streetAddressField.setPromptText(null);
			this.pcAddressField.setPromptText(null);
			this.cityAddressField.setPromptText(null);
			this.regionAddressField.setPromptText(null);
			this.countryAddressField.setPromptText(null);
		}
	}
	
	/*
	 * Rendre l'intéraction avec certains boutons de l'interface possible ou non
	 * @param : none -> Vrai si aucune personne est sélectionnée, Faux dans l'autre cas
	 * 			disable -> Vrai si l'utilisateur modifie les informations d'une personne, Faux dans l'autre cas
	 */
	private void disableButton(boolean none, boolean disable) {
		this.addButton.setDisable(disable);
		this.updateButton.setDisable(none ? !disable : disable);
		this.deleteButton.setDisable(none ? !disable : disable);
		this.filterButton.setDisable(disable);
		this.saveButton.setDisable(!disable);
		this.cancelButton.setDisable(!disable);
	}
	
	/*
	 * Modifier la photo associée à une personne
	 */
	public void changePhoto() throws MalformedURLException {
		File imageFile = photoFile();
		if (imageFile != null) {
			this.urlChangePhoto = imageFile.toURI().toURL().toString();
			Image photo = new Image(this.urlChangePhoto);
			//System.out.println(ContactApp.class.getResource("image/homescreen.jpg").getClass());
			this.photoImageView.setImage(photo);
		}
	}
	
	/*
	 * Importer le lien vers la photo à afficher
	 */
	public File photoFile() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select the photo to import");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
		File file = null;
        try{
        	try{
        		file = fileChooser.showOpenDialog(StageService.getPrimaryStage());
	        }catch(IllegalArgumentException e){
	        	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
	        	file = fileChooser.showOpenDialog(StageService.getPrimaryStage());
	        }
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
        return file;
	}
	
}