package test;

import java.util.Iterator;
import java.util.List;

import database.AbstractDAO;
import database.RemoteDAO;
import modelPersistent.Address;
import modelPersistent.Remote;

public class TestClass {

	public static void main(String[] args){
		AbstractDAO mgr = new AbstractDAO<>(Remote.class);
		
		
		
		Remote remote = (Remote) mgr.findOne(1);
		System.out.println(remote.getFrequency());

	}

}
