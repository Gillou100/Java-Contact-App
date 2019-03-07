package isen.java_contact_app.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Category {
	FAMILY ("Family"),
	FRIEND ("Friend"),
	WORK ("Work");
	
	private String category;
	
	private Category(String category)
	{
		this.category = category;
	}
	
	static private ObservableList<Category> categoryList;
	
	public static final ObservableList<Category> initCategoryList() {
		categoryList = FXCollections.observableArrayList();
		categoryList.add(Category.FAMILY);
		categoryList.add(Category.FRIEND);
		categoryList.add(Category.WORK);
		return categoryList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return category;
	}
}

