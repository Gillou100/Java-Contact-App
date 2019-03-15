package isen.java_contact_app.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import isen.java_contact_app.daos.DataSourceFactory;
import isen.java_contact_app.entities.User;
import isen.java_contact_app.entities.Person;
import isen.java_contact_app.entities.Address;

public class UserDao {

	static public ObservableList<User> usersList(){
		ObservableList<User> userList = new ObservableList<User>();
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement stmt = cnx.prepareStatement(
					"SELECT * FROM film JOIN genre ON film.genre_id = genre.idgenre WHERE genre.name = ?")) {
				stmt.setString(1, genreName);
				try (ResultSet results = stmt.executeQuery()) {
					while (results.next()) {
						Film film = new Film(results.getInt("idfilm"), results.getString("title"),
								results.getDate("release_date").toLocalDate(),
								new Genre(results.getInt("genre_id"), results.getString("name")),
								Integer.valueOf(results.getInt("duration")), results.getString("director"),
								results.getString("summary"));
						byGenre.add(film);
					}
					return byGenre;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Oops", e);
		}
	}
	
	static public void addUser(User newUser) {
		// TODO Auto-generated method stub		
	}
}
