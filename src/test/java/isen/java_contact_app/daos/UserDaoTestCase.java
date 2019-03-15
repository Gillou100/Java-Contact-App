package isen.java_contact_app.daos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isen.java_contact_app.daos.DataSourceFactory;
import isen.java_contact_app.daos.UserDao;
import isen.java_contact_app.entities.User;

public class UserDaoTestCase {

	private UserDao userDao = new UserDao();

	@Before
	public void initDatabase() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM film");
		stmt.executeUpdate("DELETE FROM user");
		
		//users.add(new User("root", "root"));
		
		stmt.executeUpdate("INSERT INTO user(iduser,name) VALUES (1,'Drama')");
		stmt.executeUpdate("INSERT INTO user(iduser,name) VALUES (2,'Comedy')");
		stmt.executeUpdate("INSERT INTO user(iduser,name) VALUES (3,'Thriller')");
		stmt.close();
		connection.close();
	}

	@Test
	public void shouldListUsers() {
		// WHEN
		List<User> users = userDao.listUsers();
		// THEN
		assertThat(users).hasSize(3);
		assertThat(users).extracting("id", "name").containsOnly(tuple(1, "Drama"), tuple(2, "Comedy"),
				tuple(3, "Thriller"));
	}
	
	@Test
	public void shouldGetUserByName() {
		// WHEN
		User user = userDao.getUser("Comedy");
		// THEN
		assertThat(user.getId()).isEqualTo(2);
		assertThat(user.getName()).isEqualTo("Comedy");
	}
	
	@Test
	public void shouldNotGetUnknownUser() {
		// WHEN
		User user = userDao.getUser("Unknown");
		// THEN
		assertThat(user).isNull();
	}
	
	@Test
	public void shouldAddUser() throws Exception {
		// WHEN 
		userDao.addUser("Western");
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE name='Western'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("iduser")).isNotNull();
		assertThat(resultSet.getString("name")).isEqualTo("Western");
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}
}
