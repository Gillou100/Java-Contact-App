package isen.java_contact_app.view;


import java.io.File;
import java.net.MalformedURLException;
import java.util.Optional;

import isen.java_contact_app.ContactApp;
import isen.java_contact_app.model.Category;
import isen.java_contact_app.model.Person;
import isen.java_contact_app.util.NicknameValueFactory;
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
	
	final Image defaultImage = new Image(ContactApp.class.getResource("image/default-avatar.jpg").toString());
	
	Person currentPerson;
	
	private boolean newPerson;
	
	@FXML
	private void handleAddButton() {
		System.out.println("Add bouton");
		Person person = new Person();
		PersonService.addPerson(person);
		this.personTable.getSelectionModel().select(person);
		this.newPerson = true;
		this.handleUpdateButton();
	}
	
	@FXML
	private void handleUpdateButton() {
		System.out.println("Update bouton");
		this.updatingPerson(true);
		this.disableButton(false, true);
	}
	
	@FXML
	private void handleDeleteButton() {
		System.out.println("Delete bouton");
		int selectedIndex = this.personTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.initOwner(StageService.getPrimaryStage());
			alert.setTitle("WARNING");
			alert.setHeaderText("You are going to delete this contact, continue ?");
			Optional<ButtonType> option = alert.showAndWait();
			if (option.get() == ButtonType.OK) {
				
		        personTable.getItems().remove(selectedIndex);
		        this.personTable.getSelectionModel().clearSelection();
		        this.disableButton(true, false);
		        this.personTable.refresh();
	    	}
	    }
	}
	
	@FXML
	private void handleSaveButton() {
		System.out.println("Save bouton");
		this.updatingPerson(false);
		this.disableButton(false, false);
		this.personTable.setDisable(false);
		this.savePersonDetails();
		this.personTable.refresh();
	}
	
	@FXML
	private void handleCancelButton() {
		System.out.println("Cancel bouton");
		this.updatingPerson(false);
		this.disableButton(false, false);
		this.showPersonDetails(currentPerson);
		if (this.newPerson == true) {
			this.newPerson = false;
			this.handleDeleteButton();
		}
	}
	
	@FXML
	private void initialize() {
		System.out.println("initialize");
		this.nicknameColumn.setCellValueFactory(new NicknameValueFactory());
		this.nameColumn.setCellValueFactory(new NameValueFactory());
		this.personTable.setItems(PersonService.getPersons());
		this.personTable.refresh();
		birthDatePicker.setShowWeekNumbers(false);
		
		this.personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {

			@Override
			public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
				System.out.println("passage");
				disableButton(false, false);
				showPersonDetails(newValue);
			}
		});
		this.categoryComboBox.setItems(Category.initCategoryList());
	}
	
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
			this.photoImageView.setImage(defaultImage);
		}
		else {
			this.lastNameField.setText(currentPerson.getLastName());
			this.firstNameField.setText(currentPerson.getFirstName());
			this.nicknameField.setText(currentPerson.getNickname());
			this.phoneNumberField.setText(currentPerson.getPhoneNumber());
			this.streetAddressField.setText(currentPerson.getRue());
			//this.pcAddressField.setText(currentPerson.getCodePostal());
			this.cityAddressField.setText(currentPerson.getVille());
			this.regionAddressField.setText(currentPerson.getRegionEtatProvince());
			this.countryAddressField.setText(currentPerson.getPays());
			this.emailAddressField.setText(currentPerson.getEmailAddress());
			this.birthDatePicker.setValue(currentPerson.getBirthDate());
			this.categoryComboBox.setValue(currentPerson.getCategory());
			if (currentPerson.getURLPhoto() == null) {
				this.photoImageView.setImage(defaultImage);
			}else {
				this.photoImageView.setImage(currentPerson.getURLPhoto());
			}
		}
		
	}
	
	private void savePersonDetails() {
		this.currentPerson.setLastName(lastNameField.getText());
		this.currentPerson.setFirstName(firstNameField.getText());
		this.currentPerson.setNickname(nicknameField.getText());
		this.currentPerson.setPhoneNumber(phoneNumberField.getText());
		/*this.currentPerson.setRue(streetAddressField.getText());
		this.currentPerson.setCodePostal(pcAddressField.getText());
		this.currentPerson.setVille(cityAddressField.getText());
		this.currentPerson.setRegionEtatProvince(regionAddressField.getText());
		this.currentPerson.setPays(countryAddressField.getText());*/
		this.currentPerson.setEmailAddress(emailAddressField.getText());
		this.currentPerson.setBirthDate(birthDatePicker.getValue());
		this.currentPerson.setCategory(categoryComboBox.getValue());
		this.currentPerson.setURLPhoto(photoImageView.getImage());		
		if (this.newPerson == true) {
			this.newPerson = false;
		}
		showPersonDetails(currentPerson);
	}
	
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
	
	private void disableButton(boolean none, boolean disable) {
		this.addButton.setDisable(disable);
		this.updateButton.setDisable(none ? !disable : disable);
		this.deleteButton.setDisable(none ? !disable : disable);
		this.saveButton.setDisable(!disable);
		this.cancelButton.setDisable(!disable);
	}
	
	public void changePhoto() throws MalformedURLException {
		File photo = photoFile();
		if (photo != null) {
			Image image = new Image(photo.toURI().toURL().toString());
			//System.out.println(ContactApp.class.getResource("image/homescreen.jpg").getClass());
			this.photoImageView.setImage(image);
		}
	}
	
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