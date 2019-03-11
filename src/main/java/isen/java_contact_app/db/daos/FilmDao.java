package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.isen.java2.db.entities.Film;
import fr.isen.java2.db.entities.Genre;

/**
 * @author Philippe Duval
 *
 *         this is our Film Data Access Class just as a reminder, this is it"s
 *         structure ; film (idfilm, title, release_date, genre_id, duration,
 *         director, summary)
 */
public class FilmDao {

	/**
	 * @return the list of all films in the database
	 */
	public List<Film> listFilms() {
		List<Film> listOfFilms = new ArrayList<>();
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try (Statement stmt = connection.createStatement()) {
				// The query does the join between the genre and the film. NEVER
				// try to get the genre with a new query to make the join
				// yourself in your code : we use a RELATIONAL database for a
				// reason ;) . It is orders or magnitude faster to do it on the
				// DB rather than in your code.
				try (ResultSet results = stmt
						.executeQuery("select * from film JOIN genre ON film.genre_id = genre.idgenre")) {
					while (results.next()) {
						// The novelty, here, is that we will instanciate TWO
						// objects from our Resultset line : a film, and a genre
						Film film = new Film(results.getInt("idfilm"), results.getString("title"),
								results.getDate("release_date").toLocalDate(),
								new Genre(results.getInt("genre_id"), results.getString("name")),
								Integer.valueOf(results.getInt("duration")), results.getString("director"),
								results.getString("summary"));
						listOfFilms.add(film);
					}
					return listOfFilms;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Oops", e);
		}
	}

	/**
	 * @param genreName
	 * @return a list of films off the corresponding genre
	 * 
	 *         Nothing fancy here, just practice with prepared statements and
	 *         variables
	 */
	public List<Film> listFilmsByGenre(String genreName) {
		List<Film> byGenre = new ArrayList<Film>();
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

	/**
	 * Method Used to save an film in the DB
	 * 
	 * @param film
	 * @return the film that was persisted, with it's ID
	 * 
	 *         Two points here : use of generated keys, and how to store the
	 *         Genre of the film
	 */
	public Film addFilm(Film film) {
		try (Connection cnx = DataSourceFactory.getDataSource().getConnection()) {
			// Here we pass an option to tell the DB that we want to get the
			// generated keys back
			try (PreparedStatement stmt = cnx.prepareStatement(
					"INSERT INTO film(title,release_date,genre_id,duration,director,summary) VALUES(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, film.getTitle());
				stmt.setDate(2, Date.valueOf(film.getReleaseDate()));
				// Nothing fancy here. we just have to grab the genre id to make
				// the link in the DB. Warning: this is a foreign key, so in
				// real life you should decide how to handle the case where the
				// genre does not exist in the DB
				stmt.setInt(3, film.getGenre().getId());
				stmt.setInt(4, film.getDuration());
				stmt.setString(5, film.getDirector());
				stmt.setString(6, film.getSummary());
				stmt.executeUpdate();
				// A little routine to grab the key and set it in our object
				try (ResultSet keys = stmt.getGeneratedKeys()) {
					keys.next();
					film.setId(keys.getInt(1));
					return film;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Oops", e);
		}
	}
}
