package fr.isen.java2.db.daos;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceFactory {

	private static MysqlDataSource dataSource;

	public static DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = new MysqlDataSource();
			dataSource.setServerName("127.0.0.1");
			dataSource.setPort(3306);
			dataSource.setDatabaseName("isen");
			dataSource.setUser("isen");
			dataSource.setPassword("isen");
		}
		return dataSource;
	}
}
