package test.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.fluttercode.datafactory.impl.DataFactory;

import database.DataSourceFactory;
import model.dataobjects.Adres;
import values.StatementsMySql;

public class GenerateDummyData {
	private static DataSource datasource = DataSourceFactory.getMySQLDataSource();
	private static DataFactory datafactory;
	private static Connection connection;
	private static PreparedStatement pstmt;

	public static void main(String[] args) {				
		try {
			createAddress();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	private static void createAddress() throws SQLException{
		Adres address = new Adres("Bondgenotenlaan", "24", 3000, "Leuven", "België");
		
		connection = datasource.getConnection();
		pstmt = connection.prepareStatement(StatementsMySql.write_Object.getValue());
		
		pstmt.setString(1, address.getClass().getName());
		pstmt.setObject(2, address);
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}

}
