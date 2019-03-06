package isen.java_contact_app.view;


import java.util.Optional;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	TextField addressField;

	@FXML
	TextField emailAddressField;
	
	@FXML
	DatePicker birthDatePicker;
	
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
				
	}
	
	private void showPersonDetails(Person person) {
		this.currentPerson = person;
		if (currentPerson == null) {
			this.lastNameField.setText(null);
			this.firstNameField.setText(null);
			this.nicknameField.setText(null);
			this.phoneNumberField.setText(null);
			this.addressField.setText(null);
			this.emailAddressField.setText(null);
			this.birthDatePicker.setValue(null);
		}
		else {
			this.lastNameField.setText(currentPerson.getLastName());
			this.firstNameField.setText(currentPerson.getFirstName());
			this.nicknameField.setText(currentPerson.getNickname());
			this.phoneNumberField.setText(currentPerson.getPhoneNumber());
			this.addressField.setText(currentPerson.getAddress());
			this.emailAddressField.setText(currentPerson.getEmailAddress());
			this.birthDatePicker.setValue(currentPerson.getBirthDate());
		}
		
	}
	
	private void savePersonDetails() {
		this.currentPerson.setLastName(lastNameField.getText());
		this.currentPerson.setFirstName(firstNameField.getText());
		this.currentPerson.setNickname(nicknameField.getText());
		this.currentPerson.setPhoneNumber(phoneNumberField.getText());
		this.currentPerson.setAddress(addressField.getText());
		this.currentPerson.setEmailAddress(emailAddressField.getText());
		this.currentPerson.setBirthDate(birthDatePicker.getValue());
		if (this.newPerson == true) {
			this.newPerson = false;
		}
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
		this.addressField.setMouseTransparent(!editable);
		this.addressField.setEditable(editable);
		this.emailAddressField.setMouseTransparent(!editable);
		this.emailAddressField.setEditable(editable);
		this.birthDatePicker.setMouseTransparent(!editable);
		this.personTable.setMouseTransparent(editable);
	}
	
	private void disableButton(boolean none, boolean disable) {
		this.addButton.setDisable(disable);
		this.updateButton.setDisable(none ? !disable : disable);
		this.deleteButton.setDisable(none ? !disable : disable);
		this.saveButton.setDisable(!disable);
		this.cancelButton.setDisable(!disable);
	}
	
}