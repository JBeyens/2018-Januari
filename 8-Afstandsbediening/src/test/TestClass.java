package test;


import database.GenericDAO;

import modelPersistent.Remote;


public class TestClass {

	public static void main(String[] args){
		
		Remote remote;
		
		GenericDAO<Remote> DAO = new GenericDAO<>(Remote.class);
		
		remote = DAO.findOne(1);
		
		DAO.delete(remote);

	}

}
