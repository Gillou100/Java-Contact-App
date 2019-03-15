package isen.java_contact_app.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Category {
	ALL,
	FAMILY,
	FRIEND,
	WORK;
	
	static private ObservableList<Category> categoryList;
	
	public static final ObservableList<Category> initCategoryList(boolean all) {
		categoryList = FXCollections.observableArrayList();
		if(all) categoryList.add(Category.ALL);
		categoryList.add(Category.FAMILY);
		categoryList.add(Category.FRIEND);
		categoryList.add(Category.WORK);
		return categoryList;
	}
	
}

