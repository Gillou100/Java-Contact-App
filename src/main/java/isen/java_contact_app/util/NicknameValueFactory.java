package isen.java_contact_app.util;

import isen.java_contact_app.entities.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class NicknameValueFactory
		implements Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>> {

	/*
	 * (non-Javadoc)
	 * @see javafx.util.Callback#call(java.lang.Object)
	 */
	@Override
	public ObservableValue<String> call(CellDataFeatures<Person, String> cellData) {
		return new SimpleStringProperty(cellData.getValue().getNickname());
	}
}