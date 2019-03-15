package isen.java_contact_app.db.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import isen.java_contact_app.db.entities.Film;
import isen.java_contact_app.db.entities.Genre;

public class FilmDaoTestCase {
	@Before
	public void initDb() throws SQLException {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM film");
		stmt.executeUpdate("DELETE FROM genre");
		stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (1,'Drama')");
		stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (2,'Comedy')");
		stmt.executeUpdate("INSERT INTO film(idfilm,title, release_date, genre_id, duration, director, summary) "
				+ "VALUES (1, 'Title 1', '2015-11-26', 1, 120, 'director 1', 'summary of the first film')");
		stmt.executeUpdate("INSERT INTO film(idfilm,title, release_date, genre_id, duration, director, summary) "
				+ "VALUES (2, 'My Title 2', '2015-11-14', 2, 114, 'director 2', 'summary of the second film')");
		stmt.executeUpdate("INSERT INTO film(idfilm,title, release_date, genre_id, duration, director, summary) "
				+ "VALUES (3, 'Third title', '2015-12-12', 2, 176, 'director 3', 'summary of the third film')");
		stmt.close();
		connection.close();
	}

	@Test
	public void shouldListFilms() throws SQLException {
		// GIVEN we have a film DAO ready to take orders
		FilmDao filmDao = new FilmDao();
		// WHEN we call our DAO to get films
		List<Film> filmList = filmDao.listFilms();
		// THEN our list should contains 3 items
		assertThat(filmList).hasSize(3).doesNotContainNull();
	}

	@Test
	public void shouldListFilmsByGenre() {
		// GIVEN we have a film DAO ready to take orders
		FilmDao filmDao = new FilmDao();
		// WHEN we call our DAO to get films with the "Comedy" Genre
		List<Film> filmList = filmDao.listFilmsByGenre("Comedy");
		// THEN our list should contains 2 items of genre Comedy
		assertThat(filmList).hasSize(2).doesNotContainNull();
		assertThat(filmList).allMatch(new Predicate<Film>() {
			@Override
			public boolean test(Film t) {

				return t.getGenre().getName().equals("Comedy");
			}
		});
	}

	@Test
	public void shouldAddFilm() throws Exception {
		// GIVEN we have a film DAO ready to take orders and a film to add
		FilmDao filmDao = new FilmDao();
		Film filmToCreate = new Film(Integer.valueOf(42), "Halloween", LocalDate.of(1978, 10, 25), new Genre(2, "Comedy"),
				Integer.valueOf(120), "John Carpenter", "Some advert for sharp knives");
		// WHEN we call our DAO to
		Film newFilm = filmDao.addFilm(filmToCreate);
		
		// THEN our film should have been persisted, and we should get back our
		// film with an ID.
		// First we verify that the record in DB is what we expected to be stored
		// Notice that we go check directly in DB if the film is correctly
		// persisted. A very bad idea would be to use one of the methods of
		// FilmDao that reads a film in the DB, as we would end up testing TWO
		// things in our test !

		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM film WHERE title='Halloween'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idfilm")).isNotNull();
		assertThat(resultSet.getString("title")).isEqualTo("Halloween");
		assertThat(resultSet.getDate("release_date").toLocalDate()).isEqualTo(LocalDate.of(1978, 10, 25));
		assertThat(resultSet.getInt("duration")).isEqualTo(120);
		assertThat(resultSet.getString("director")).isEqualTo("John Carpenter");
		assertThat(resultSet.getString("summary")).isEqualTo("Some advert for sharp knives");
		
		// ...and so on... you get the idea...
		assertThat(resultSet.next()).isFalse(); // only one film persisted in DB
		resultSet.close();
		statement.close();
		connection.close();
		// Now check the film that we got back
		assertThat(newFilm).isNotNull(); // We got a film
		assertThat(newFilm.getId()).isNotNull(); // the film has an id
		
		// the film that we got back is the same that the one we stored
		assertThat(newFilm.getTitle()).isEqualTo(filmToCreate.getTitle());
		assertThat(newFilm.getSummary()).isEqualTo(filmToCreate.getSummary());
		assertThat(newFilm.getReleaseDate()).isEqualTo(filmToCreate.getReleaseDate());
		assertThat(newFilm.getGenre()).isEqualTo(filmToCreate.getGenre());
		assertThat(newFilm.getDuration()).isEqualTo(filmToCreate.getDuration());
		assertThat(newFilm.getDirector()).isEqualTo(filmToCreate.getDirector());
	}
}
