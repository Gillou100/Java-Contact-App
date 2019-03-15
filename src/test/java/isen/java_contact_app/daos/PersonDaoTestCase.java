package isen.java_contact_app.daos;

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

import isen.java_contact_app.daos.DataSourceFactory;
import isen.java_contact_app.daos.PersonDao;
import isen.java_contact_app.entities.Address;
import isen.java_contact_app.entities.Person;
import isen.java_contact_app.util.Category;

public class PersonDaoTestCase {
	@Before
	public void initDb() throws SQLException {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("DELETE FROM address");

		//personsDataBase.add(new Person(1, "lastname1", "firstname1", "nickname1", "0665487562", new Address("", "", "address1", "", "", -1, ""), "email_address_1", LocalDate.now(), Category.FAMILY/*categories1*/, null));
		//personsDataBase.add(new Person(1, "lastname2", "firstname2", "nickname2", "0654423025", new Address("", "", "address2", "", "", -1, ""), "email_address_2", LocalDate.now(), null/*categories2*/, null));
		//personsDataBase.add(new Person(1, "lastname3", "firstname3", "nickname3", "0744512325", new Address("", "", "address3", "", "", -1, ""), "email_address_3", LocalDate.now(), Category.WORK/*categories3*/, "C"));

		stmt.executeUpdate("INSERT INTO address(idaddress,name) VALUES (1,'Drama')");
		stmt.executeUpdate("INSERT INTO address(idaddress,name) VALUES (2,'Comedy')");
		stmt.executeUpdate("INSERT INTO address(idaddress,name) VALUES (2,'Comedy')");
		
		stmt.executeUpdate("INSERT INTO person(idperson,title, release_date, address_id, duration, director, summary) "
				+ "VALUES (1, 'Title 1', '2015-11-26', 1, 120, 'director 1', 'summary of the first person')");
		stmt.executeUpdate("INSERT INTO person(idperson,title, release_date, address_id, duration, director, summary) "
				+ "VALUES (2, 'My Title 2', '2015-11-14', 2, 114, 'director 2', 'summary of the second person')");
		stmt.executeUpdate("INSERT INTO person(idperson,title, release_date, address_id, duration, director, summary) "
				+ "VALUES (3, 'Third title', '2015-12-12', 2, 176, 'director 3', 'summary of the third person')");
		stmt.close();
		connection.close();
	}

	@Test
	public void shouldListPersons() throws SQLException {
		// GIVEN we have a person DAO ready to take orders
		PersonDao personDao = new PersonDao();
		// WHEN we call our DAO to get persons
		List<Person> personList = personDao.listPersons();
		// THEN our list should contains 3 items
		assertThat(personList).hasSize(3).doesNotContainNull();
	}

	@Test
	public void shouldListPersonsByCurrentUser() {
		// GIVEN we have a person DAO ready to take orders
		PersonDao personDao = new PersonDao();
		// WHEN we call our DAO to get persons with the "Comedy" username
		List<Person> personList = personDao.listPersonsByaddress("Comedy");
		// THEN our list should contains 1 items of address Comedy
		assertThat(personList).hasSize(2).doesNotContainNull();
		assertThat(personList).allMatch(new Predicate<Person>() {
			@Override
			public boolean test(Person t) {

				return t.getaddress().getName().equals("Comedy");
			}
		});
	}

	@Test
	public void shouldAddPerson() throws Exception {
		// GIVEN we have a person DAO ready to take orders and a person to add
		PersonDao personDao = new PersonDao();
		Person personToCreate = new Person(Integer.valueOf(42), "Halloween", LocalDate.of(1978, 10, 25), new address(2, "Comedy"),
				Integer.valueOf(120), "John Carpenter", "Some advert for sharp knives");
		// WHEN we call our DAO to
		Person newPerson = personDao.addPerson(personToCreate);
		
		// THEN our person should have been persisted, and we should get back our
		// person with an ID.
		// First we verify that the record in DB is what we expected to be stored
		// Notice that we go check directly in DB if the person is correctly
		// persisted. A very bad idea would be to use one of the methods of
		// PersonDao that reads a person in the DB, as we would end up testing TWO
		// things in our test !

		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE title='Halloween'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("title")).isEqualTo("Halloween");
		assertThat(resultSet.getDate("release_date").toLocalDate()).isEqualTo(LocalDate.of(1978, 10, 25));
		assertThat(resultSet.getInt("duration")).isEqualTo(120);
		assertThat(resultSet.getString("director")).isEqualTo("John Carpenter");
		assertThat(resultSet.getString("summary")).isEqualTo("Some advert for sharp knives");
		
		// ...and so on... you get the idea...
		assertThat(resultSet.next()).isFalse(); // only one person persisted in DB
		resultSet.close();
		statement.close();
		connection.close();
		// Now check the person that we got back
		assertThat(newPerson).isNotNull(); // We got a person
		assertThat(newPerson.getId()).isNotNull(); // the person has an id
		
		// the person that we got back is the same that the one we stored
		assertThat(newPerson.getTitle()).isEqualTo(personToCreate.getTitle());
		assertThat(newPerson.getSummary()).isEqualTo(personToCreate.getSummary());
		assertThat(newPerson.getReleaseDate()).isEqualTo(personToCreate.getReleaseDate());
		assertThat(newPerson.getaddress()).isEqualTo(personToCreate.getaddress());
		assertThat(newPerson.getDuration()).isEqualTo(personToCreate.getDuration());
		assertThat(newPerson.getDirector()).isEqualTo(personToCreate.getDirector());
	}
}
