package database;

import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import model.properties.PropertyFileReader;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 03/12/2017
	@Project Afstandsbediening
	@Doel Factory to create DataSource. Load diver/user/... from properties file
 */

public class DataSourceFactory {
	
	public static DataSource getMySQLDataSource() {
		Properties props = new Properties();
		MysqlDataSource mysqlDS = null;
		
		try {
			props = PropertyFileReader.loadProperties("resources/database.properties");
			mysqlDS = new MysqlDataSource();
			mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
			mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
			mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mysqlDS;
	}
}
