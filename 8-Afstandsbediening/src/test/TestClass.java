package test;

import database.GenericDAO;
import modelPersistent.Address;


public class TestClass {

	public static void main(String[] args){
		GenericDAO<Address> mgr = new GenericDAO<>(Address.class);
		
		
		Address adres = new Address("cssssssssc", 10, 5, 3000, "Leuven", "België");
		
		mgr.create(adres);

	}

}
