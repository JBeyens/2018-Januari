package test.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.sql.DataSource;

import org.fluttercode.datafactory.impl.DataFactory;

import database.DataSourceFactory;
import model.dataobjects.Adres;
import model.dataobjects.Remote;
import values.StatementsMySql;

public class GenerateDummyData {
	private static DataSource datasource = DataSourceFactory.getMySQLDataSource();
	private static DataFactory datafactory;
	private static Connection connection;
	private static PreparedStatement pstmt;

	public static void main(String[] args) {				
		try {
			createAddress();
			createAfstandsbediening();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	private static void createAddress() throws SQLException{
		Adres address = new Adres("Bondgenotenlaan", "24", 3000, "Leuven", "België");

		try {
			connection = datasource.getConnection();
			pstmt = connection.prepareStatement(StatementsMySql.write_Object_Address.getValue());
			
			pstmt.setString(1, address.getStreet());
			pstmt.setString(2, address.getNumber());
			pstmt.setInt(3, address.getPostalCode());
			pstmt.setString(4, address.getCity());
			pstmt.setString(5, address.getCountry());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			pstmt.close();
			connection.close();
		}	
	}
	
	private static void createAfstandsbediening() throws SQLException{
		connection = datasource.getConnection();
		pstmt = connection.prepareStatement(StatementsMySql.write_Object_Afstandsbediening.getValue());
		
		for (int i = 0; i < 20; i++) {
			String serial = UUID.randomUUID().toString();
			Remote remote = new Remote(serial, (long)ThreadLocalRandom.current().nextLong(100000, 10000000));
				
			try {
				pstmt.setString(1, remote.getSerialNumber());
				pstmt.setLong(2, remote.getFrequency());
				pstmt.executeUpdate();				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			}
		pstmt.close();
		connection.close();
	}

}
