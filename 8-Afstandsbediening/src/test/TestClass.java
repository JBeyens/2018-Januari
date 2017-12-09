package test;


import database.GenericDAO;
import modelPersistent.Address;




public class TestClass {

	public static void main(String[] args){
		
		GenericDAO<Address> AddressDAO = new GenericDAO<>(Address.class);
		
		Address adres = new Address("Bondgenotenlaan", 150, 1, 3000, "Leuven", "België");
		AddressDAO.create(adres);
		
		

	}

}
