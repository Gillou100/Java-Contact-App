package isen.java_contact_app.db.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isen.java_contact_app.db.entities.Genre;

/**
 * @author Philippe Duval
 * 
 *         this is our Genre Data Access Class
 */
public class GenreDao {

	/**
	 * @return All genres
	 * 
	 *         this method is straightforward to implement
	 */
	public List<Genre> listGenres() {
		// Instantiate the list to return
		List<Genre> genres = new ArrayList<Genre>();
		// get a new connection
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			// get a new statement
			try (Statement stmt = cnx.createStatement()) {
				// get a new resultset
				try (ResultSet res = stmt.executeQuery("SELECT * FROM genre")) {
					while (res.next()) {
						// we iterate on the resultset to create a new genre
						// from the resuultset values and put it into the list
						genres.add(new Genre(res.getInt("idgenre"), res.getString("name")));
					}
				}
			}

		} catch (SQLException e) {
			// All the following code is a VERY BAD IDEA !!! why ?
			e.printStackTrace();
		}
		// because this can be empty if the connection to the database breaks.
		// Do you really want your program survive to a database loss ?
		return genres;
	}

	/**
	 * Gets a genre by it's name
	 * 
	 * @param name
	 * @return the corresponding genre.
	 * 
	 *         This method works as the previous method, except for the
	 *         exception catching
	 */
	public Genre getGenre(String name) {
		Genre genre = new Genre();
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			// This time we will use a prepared statement, as we have variables
			// to pass to the request
			try (PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM genre WHERE name = ?")) {
				stmt.setString(1, name);
				try (ResultSet result = stmt.executeQuery()) {
					// We take care of the case where the name returns nothing
					if (result.next()) {
						genre.setId(result.getInt("idgenre"));
						genre.setName(result.getString("name"));
						return genre;
					} else {
						// Here we choose to return null, but we could also
						// return an empty genre. Both cases are not satisfying.
						// This is up to you to choose
						// what you will be returning, taking into account that
						// the caller has to be able to use it safely
						return null;
					}
				}
			}
		} catch (SQLException e) {
			// This is a safer way to handle SQLException
			throw new RuntimeException("Something went horribly wrong with our DB and you cannot do much about it !",
					e);
		}
	}

	/**
	 * Adds a new genre
	 * 
	 * @param name
	 */
	public void addGenre(String name) {
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			try (PreparedStatement stmt = cnx.prepareStatement("INSERT INTO genre(name) VALUES( ? )")) {
				stmt.setString(1, name);
				// noting fancy in this method, except for the following
				// execcuteUpdate() instead of execcuteQuery(), since we are
				// writing to the DB
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Something went horribly wrong with our DB and you cannot do much about it !",
					e);
		}
	}
}
