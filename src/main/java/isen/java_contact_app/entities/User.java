package isen.java_contact_app.entities;

public class User{
	
	private Integer id;
	private String username;
	private String password;
	private String pathFolderContact;
	
	public User(String username, String password) {
		this.id = -1;
		this.username = username;
		this.password = password;
		this.pathFolderContact = "user.home";
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPathFolderContact(String pathFolderContact) {
		this.pathFolderContact = pathFolderContact;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPathFolderContact() {
		return pathFolderContact;
	}
	
	public boolean equals(User user) {
		return (this.username.equals(user.username) && this.password.equals(user.password));
	}
}