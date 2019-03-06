package isen.java_contact_app.service;

import java.io.IOException;

import isen.java_contact_app.ContactApp;
import javafx.fxml.FXMLLoader;

public class ViewService {
	
	public static String actualView;

	public static <T> T getView(String id) {
		actualView = id;
		return getLoader(id).getRoot();
	}

	private static FXMLLoader getLoader(String id) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ContactApp.class.getResource("view/" + id + ".fxml"));			
			loader.load();
			return loader;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
