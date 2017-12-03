package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

public class AbstractDAO {
	private DataSource datasource;
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public AbstractDAO(DataSource datasource){
		this.datasource = datasource;
	}
}
