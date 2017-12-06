package test.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.sql.DataSource;

import org.fluttercode.datafactory.impl.DataFactory;

import database.DataSourceFactory;
import model.dataobjects.Adres;
import model.dataobjects.Person;
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
			createRemote();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	private static void createAddress() throws SQLException{
//		Adres address = new Adres("Bondgenotenlaan", 24, 3000, "Leuven", "België");
//
//		try {
//			connection = datasource.getConnection();
//			pstmt = connection.prepareStatement(StatementsMySql.write_Object_Address.getValue());
//			
//			pstmt.setString(1, address.getStreet());
//			pstmt.setInt(2, address.getNumber());
//			pstmt.setInt(3, address.getPostalCode());
//			pstmt.setString(4, address.getCity());
//			pstmt.setString(5, address.getCountry());
//			pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally{
//			pstmt.close();
//			connection.close();
//		}	
	}
	
	private static void createRemote() throws SQLException{
		connection = datasource.getConnection();
		pstmt = connection.prepareStatement(StatementsMySql.write_Object_Remote.getValue());
		
		for (int i = 0; i < 20; i++) {
			Remote remote = new Remote(UUID.randomUUID().toString(), (long)ThreadLocalRandom.current().nextLong(100000, 10000000));
				
			try {
				pstmt.setString(1, remote.getSerialNumber());
				pstmt.setLong(2, remote.getFrequency());
				pstmt.setNull(3,  java.sql.Types.INTEGER);
				pstmt.executeUpdate();				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			}
		pstmt.close();
		connection.close();
	}
	
	private static void createPerson() throws Exception{
//		connection = datasource.getConnection();
//		pstmt = connection.prepareStatement(StatementsMySql.read_Objects_RemoteID_No_Person_Assigned.getValue());
//		
//		try(ResultSet rs = pstmt.executeQuery()){
//			if(rs.next()){
//				pstmt.close();
//				pstmt = connection.prepareStatement(StatementsMySql.write_Object_Person.getValue());
//				
//				Person person = new Person(datafactory.getFirstName(), datafactory.getLastName(), , , null);
//			}
//			else{
//				throw new Exception("Er zijn geen beschikare Remotes");
//			}
//		}
//		
//		pstmt.close();
//		connection.close();
	}

}
