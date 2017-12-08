package test;

import java.util.Iterator;
import java.util.List;

import database.GenericDAO;
import modelPersistent.Address;
import modelPersistent.Remote;

public class TestClass {

	public static void main(String[] args){
		GenericDAO mgr = new GenericDAO<>(Address.class);
		
		
		Address adres = new Address("TestTest", 10, 5, 3000, "Leuven", "België");
		
		mgr.create(adres);

	}

}
